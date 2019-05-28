
package gui;

import bll.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import org.jdatepicker.impl.*;

public final class admin extends javax.swing.JFrame {

    JDatePickerImpl dpFromDate = datePicker();
    JDatePickerImpl dpToDate = datePicker();
    
    accountBLL accBLL = new accountBLL();
    billBLL bBLL = new billBLL();
    billinfoBLL biBLL = new billinfoBLL();
    categoryBLL cgBLL = new categoryBLL();
    drinkBLL drBLL = new drinkBLL();
    tableBLL tbBLL = new tableBLL();
    
    public admin() throws SQLException, ParseException {
        initComponents();
        getDatePickerIntoBillPanel();
        getPayedBillListByDate();
        getDrinkList();
        getCategoryIntoCB();
        getCategoryList();
        getTableList();
        getStatusTableIntoCB();
        getAccountList();
        getTypeAccountIntoCB();
    }
    
    public static JDatePickerImpl datePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties prop = new Properties();
        prop.put("text.today", "Today");
        prop.put("text.month", "Month");
        prop.put("text.year", "Year");
        java.util.Date date = new java.util.Date();
        model.setValue(date);
        JDatePanelImpl datePanel = new JDatePanelImpl(model, prop);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new dto.date());
        datePicker.setBounds(0, 0, 200, 23);
        return datePicker;
    }
    
    public void getDatePickerIntoBillPanel() {
        pnFromDate.add(dpFromDate);
        pnToDate.add(dpToDate);
    }
    
    public void getPayedBillListByDate() throws SQLException, ParseException {
        tbBill.removeAll();
        DefaultTableModel tbModel = new DefaultTableModel();
        String colsName[] = {"Table", "Date Checkin", "Date Checkout", "Discount (%)", "Total Price"};
        tbModel.setColumnIdentifiers(colsName);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDate = dateFormat.format(dpFromDate.getModel().getValue());
        String toDate = dateFormat.format(dpToDate.getModel().getValue());
        ArrayList<Vector> billList = bBLL.getBillListByDate(fromDate, toDate);
        for (Vector b : billList) {
            Vector rowData = new Vector();
            rowData.add(b.get(0));
            dateFormat = new SimpleDateFormat("dd MM, yyyy");
            rowData.add(dateFormat.format(b.get(1)));
            rowData.add(dateFormat.format(b.get(2)));
            rowData.add(b.get(3));
            rowData.add(b.get(4) + " đ");
            tbModel.addRow(rowData);
        }
        tbBill.setModel(tbModel);
    }
    
    public void getDrinkList() throws SQLException {
        tbDrink.removeAll();
        DefaultTableModel tbModel = new DefaultTableModel();
        String colsName[] = {"ID", "Name", "Price", "ID Category"};
        tbModel.setColumnIdentifiers(colsName);
        ArrayList<dto.drink> drinkList = drBLL.getDrinkList();
        for (dto.drink dr : drinkList) {
            Vector rowData = new Vector();
            rowData.add(Integer.toString(dr.getID()));
            rowData.add(dr.getName());
            rowData.add(Float.toString(dr.getPrice()));
            rowData.add(Integer.toString(dr.getIDCategory()));
            tbModel.addRow(rowData);
        }
        tbDrink.setModel(tbModel);
    }
    
    public void getCategoryIntoCB() throws SQLException {
        ArrayList<dto.category> categoryList = cgBLL.getCategoryList();
        cbCategoryDrink.setModel(new DefaultComboBoxModel<>(categoryList.toArray(new dto.category[0])));
    }
    
    public void getCategoryList() throws SQLException {
        tbCategory.removeAll();
        DefaultTableModel tbModel = new DefaultTableModel();
        String colsName[] = {"ID", "Name"};
        tbModel.setColumnIdentifiers(colsName);
        ArrayList<dto.category> categoryList = cgBLL.getCategoryList();
        for (dto.category cg : categoryList) {
            Vector rowData = new Vector();
            rowData.add(Integer.toString(cg.getID()));
            rowData.add(cg.getName());
            tbModel.addRow(rowData);
        }
        tbCategory.setModel(tbModel);
    }
    
    public void getTableList() throws SQLException {
        tbTable.removeAll();
        DefaultTableModel tbModel = new DefaultTableModel();
        String colsName[] = {"ID", "Name", "Status"};
        tbModel.setColumnIdentifiers(colsName);
        ArrayList<dto.table> tableList = tbBLL.getTableList();
        for (dto.table tb : tableList) {
            Vector rowData = new Vector();
            rowData.add(Integer.toString(tb.getID()));
            rowData.add(tb.getName());
            rowData.add(tb.getStatus());
            tbModel.addRow(rowData);
        }
        tbTable.setModel(tbModel);
    }
    
    public void getStatusTableIntoCB() throws SQLException {
        cbStatusTable.addItem("Empty");
        cbStatusTable.addItem("Guest");
    }
    
    public void getAccountList() throws SQLException {
        tbAccount.removeAll();
        DefaultTableModel tbModel = new DefaultTableModel();
        String colsName[] = {"ID", "User Name", "Display Name", "Type"};
        tbModel.setColumnIdentifiers(colsName);
        ArrayList<dto.account> accountList = accBLL.getAccountList();
        for (dto.account acc : accountList) {
            Vector rowData = new Vector();
            rowData.add(Integer.toString(acc.getID()));
            rowData.add(acc.getUserName());
            rowData.add(acc.getDisplayName());
            rowData.add(acc.getTypeName());
            tbModel.addRow(rowData);
        }
        tbAccount.setModel(tbModel);
    }
    
    public void getTypeAccountIntoCB() {
        cbTypeAccount.addItem("admin");
        cbTypeAccount.addItem("staff");
    }
    
    public void setDataBinding(JTable table, ArrayList<JTextField> tfList) {
        int sltRow = table.getSelectedRow();
        String dataBinding = new String();
        for (int i = 0; i < tfList.size(); i++) {
            tfList.get(i).setText((String)table.getValueAt(sltRow, i));
        }
    }
    
    @SuppressWarnings("empty-statement")
    public void cbIDCategoryDataBinding() throws SQLException {
        int id = Integer.parseInt(Objects.toString(tbDrink.getValueAt(tbDrink.getSelectedRow(), 3)));
        dto.category cg = cgBLL.getCategoryByIDDrink(id);
        cbCategoryDrink.getModel().setSelectedItem(cg);
    }
    
    public void cbStatusTableDataBinding() {
        String status = (String)tbTable.getValueAt(tbTable.getSelectedRow(), 2);
        int isEmpty = status.equals("Empty") ? 0 : 1;
        cbStatusTable.setSelectedIndex(isEmpty);
    }
    
    public void cbTypeAccountDataBinding() {
        String type = (String)tbAccount.getValueAt(tbAccount.getSelectedRow(), 3);
        int isAdmin = type.equals("admin") ? 0 : 1;
        cbTypeAccount.setSelectedIndex(isAdmin);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpAdmin = new javax.swing.JTabbedPane();
        tpBill = new javax.swing.JPanel();
        pnBill = new javax.swing.JPanel();
        pnFromDate = new javax.swing.JPanel();
        pnToDate = new javax.swing.JPanel();
        btnShow = new javax.swing.JButton();
        spBill = new javax.swing.JScrollPane();
        tbBill = new javax.swing.JTable();
        tpDrink = new javax.swing.JPanel();
        btnAddDrink = new javax.swing.JButton();
        btnRemoveDrink = new javax.swing.JButton();
        btnUpdateDrink = new javax.swing.JButton();
        spTableDrink = new javax.swing.JScrollPane();
        tbDrink = new javax.swing.JTable();
        txtSearchDrink = new javax.swing.JTextField();
        btnSearchDrink = new javax.swing.JButton();
        lbIDDrink = new javax.swing.JLabel();
        lbNameDrink = new javax.swing.JLabel();
        lbCategoryDrink = new javax.swing.JLabel();
        lbPriceDrink = new javax.swing.JLabel();
        txtIDDrink = new javax.swing.JTextField();
        txtNameDrink = new javax.swing.JTextField();
        cbCategoryDrink = new javax.swing.JComboBox<>();
        txtPriceDrink = new javax.swing.JTextField();
        tpCategory = new javax.swing.JPanel();
        txtNameCategory = new javax.swing.JTextField();
        lbNameCategory = new javax.swing.JLabel();
        lbIDCategory = new javax.swing.JLabel();
        txtIDCategory = new javax.swing.JTextField();
        btnUpdateCategory = new javax.swing.JButton();
        spCategory = new javax.swing.JScrollPane();
        tbCategory = new javax.swing.JTable();
        btnRemoveCategory = new javax.swing.JButton();
        btnAddCategory = new javax.swing.JButton();
        tpTable = new javax.swing.JPanel();
        spTable = new javax.swing.JScrollPane();
        tbTable = new javax.swing.JTable();
        btnAddTable = new javax.swing.JButton();
        btnRemoveTable = new javax.swing.JButton();
        btnUpdateTable = new javax.swing.JButton();
        txtIDTable = new javax.swing.JTextField();
        lbIDTable = new javax.swing.JLabel();
        lbNameTable = new javax.swing.JLabel();
        txtNameTable = new javax.swing.JTextField();
        cbStatusTable = new javax.swing.JComboBox<>();
        lbStatusTable = new javax.swing.JLabel();
        tpAccount = new javax.swing.JPanel();
        btnAddAccount = new javax.swing.JButton();
        spAccount = new javax.swing.JScrollPane();
        tbAccount = new javax.swing.JTable();
        btnRemoveAccount = new javax.swing.JButton();
        btnUpdateAccount = new javax.swing.JButton();
        txtIDAccount = new javax.swing.JTextField();
        lbIDAccount = new javax.swing.JLabel();
        lbNameAccount = new javax.swing.JLabel();
        lbTypeAccount = new javax.swing.JLabel();
        cbTypeAccount = new javax.swing.JComboBox<>();
        txtNameAccount = new javax.swing.JTextField();
        btnResetPassword = new javax.swing.JButton();
        txtUserAccount = new javax.swing.JTextField();
        lbUserAccount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(576, 436));
        setResizable(false);
        setSize(new java.awt.Dimension(576, 436));

        javax.swing.GroupLayout pnFromDateLayout = new javax.swing.GroupLayout(pnFromDate);
        pnFromDate.setLayout(pnFromDateLayout);
        pnFromDateLayout.setHorizontalGroup(
            pnFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        pnFromDateLayout.setVerticalGroup(
            pnFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        pnToDate.setPreferredSize(new java.awt.Dimension(200, 30));

        javax.swing.GroupLayout pnToDateLayout = new javax.swing.GroupLayout(pnToDate);
        pnToDate.setLayout(pnToDateLayout);
        pnToDateLayout.setHorizontalGroup(
            pnToDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        pnToDateLayout.setVerticalGroup(
            pnToDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        btnShow.setLabel("Show");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        tbBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        spBill.setViewportView(tbBill);

        javax.swing.GroupLayout pnBillLayout = new javax.swing.GroupLayout(pnBill);
        pnBill.setLayout(pnBillLayout);
        pnBillLayout.setHorizontalGroup(
            pnBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBillLayout.createSequentialGroup()
                .addGroup(pnBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnBillLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(pnFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnShow, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                    .addGroup(pnBillLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(spBill)))
                .addContainerGap())
        );
        pnBillLayout.setVerticalGroup(
            pnBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBillLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnShow)
                    .addComponent(pnToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spBill, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnShow.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout tpBillLayout = new javax.swing.GroupLayout(tpBill);
        tpBill.setLayout(tpBillLayout);
        tpBillLayout.setHorizontalGroup(
            tpBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tpBillLayout.setVerticalGroup(
            tpBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tpAdmin.addTab("Bill", tpBill);

        btnAddDrink.setText("Add");
        btnAddDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDrinkActionPerformed(evt);
            }
        });

        btnRemoveDrink.setText("Remove");
        btnRemoveDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveDrinkActionPerformed(evt);
            }
        });

        btnUpdateDrink.setText("Update");
        btnUpdateDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDrinkActionPerformed(evt);
            }
        });

        tbDrink.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDrink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDrinkMouseClicked(evt);
            }
        });
        spTableDrink.setViewportView(tbDrink);

        btnSearchDrink.setText("Search");
        btnSearchDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchDrinkActionPerformed(evt);
            }
        });

        lbIDDrink.setText("ID:");

        lbNameDrink.setText("Drink:");

        lbCategoryDrink.setText("Category:");

        lbPriceDrink.setText("Price:");

        txtIDDrink.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIDDrink.setEnabled(false);

        javax.swing.GroupLayout tpDrinkLayout = new javax.swing.GroupLayout(tpDrink);
        tpDrink.setLayout(tpDrinkLayout);
        tpDrinkLayout.setHorizontalGroup(
            tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpDrinkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tpDrinkLayout.createSequentialGroup()
                        .addComponent(btnAddDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnRemoveDrink)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spTableDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tpDrinkLayout.createSequentialGroup()
                        .addComponent(txtSearchDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tpDrinkLayout.createSequentialGroup()
                        .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNameDrink)
                            .addComponent(lbPriceDrink)
                            .addComponent(lbCategoryDrink)
                            .addComponent(lbIDDrink))
                        .addGap(30, 30, 30)
                        .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtIDDrink)
                            .addComponent(txtNameDrink, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbCategoryDrink, javax.swing.GroupLayout.Alignment.LEADING, 0, 117, Short.MAX_VALUE)
                            .addComponent(txtPriceDrink, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        tpDrinkLayout.setVerticalGroup(
            tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpDrinkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddDrink)
                        .addComponent(btnUpdateDrink)
                        .addComponent(txtSearchDrink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchDrink))
                    .addComponent(btnRemoveDrink, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpDrinkLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spTableDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(tpDrinkLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbIDDrink)
                            .addComponent(txtIDDrink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNameDrink)
                            .addComponent(txtNameDrink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbCategoryDrink)
                            .addComponent(cbCategoryDrink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbPriceDrink)
                            .addComponent(txtPriceDrink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(131, Short.MAX_VALUE))))
        );

        tpAdmin.addTab("Drink", tpDrink);

        lbNameCategory.setText("Name:");

        lbIDCategory.setText("ID:");

        txtIDCategory.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIDCategory.setEnabled(false);

        btnUpdateCategory.setText("Update");
        btnUpdateCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCategoryActionPerformed(evt);
            }
        });

        tbCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCategoryMouseClicked(evt);
            }
        });
        spCategory.setViewportView(tbCategory);

        btnRemoveCategory.setText("Remove");
        btnRemoveCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveCategoryActionPerformed(evt);
            }
        });

        btnAddCategory.setText("Add");
        btnAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tpCategoryLayout = new javax.swing.GroupLayout(tpCategory);
        tpCategory.setLayout(tpCategoryLayout);
        tpCategoryLayout.setHorizontalGroup(
            tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tpCategoryLayout.createSequentialGroup()
                        .addComponent(btnAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnRemoveCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNameCategory)
                    .addComponent(lbIDCategory))
                .addGap(46, 46, 46)
                .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtIDCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(txtNameCategory, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        tpCategoryLayout.setVerticalGroup(
            tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddCategory)
                        .addComponent(btnUpdateCategory))
                    .addComponent(btnRemoveCategory, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpCategoryLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(tpCategoryLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbIDCategory)
                            .addComponent(txtIDCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNameCategory)
                            .addComponent(txtNameCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(223, Short.MAX_VALUE))))
        );

        tpAdmin.addTab("Category", tpCategory);

        tbTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTableMouseClicked(evt);
            }
        });
        spTable.setViewportView(tbTable);

        btnAddTable.setText("Add");
        btnAddTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTableActionPerformed(evt);
            }
        });

        btnRemoveTable.setText("Remove");
        btnRemoveTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTableActionPerformed(evt);
            }
        });

        btnUpdateTable.setText("Update");
        btnUpdateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTableActionPerformed(evt);
            }
        });

        txtIDTable.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIDTable.setEnabled(false);

        lbIDTable.setText("ID:");

        lbNameTable.setText("Name:");

        lbStatusTable.setText("Status:");

        javax.swing.GroupLayout tpTableLayout = new javax.swing.GroupLayout(tpTable);
        tpTable.setLayout(tpTableLayout);
        tpTableLayout.setHorizontalGroup(
            tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tpTableLayout.createSequentialGroup()
                        .addComponent(btnAddTable, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnRemoveTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateTable, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNameTable)
                    .addComponent(lbStatusTable)
                    .addComponent(lbIDTable))
                .addGap(43, 43, 43)
                .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtIDTable)
                    .addComponent(txtNameTable, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbStatusTable, javax.swing.GroupLayout.Alignment.LEADING, 0, 117, Short.MAX_VALUE))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        tpTableLayout.setVerticalGroup(
            tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddTable)
                        .addComponent(btnUpdateTable))
                    .addComponent(btnRemoveTable, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpTableLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(tpTableLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbIDTable)
                            .addComponent(txtIDTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNameTable)
                            .addComponent(txtNameTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbStatusTable)
                            .addComponent(cbStatusTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(176, Short.MAX_VALUE))))
        );

        tpAdmin.addTab("Table", tpTable);

        btnAddAccount.setText("Add");
        btnAddAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAccountActionPerformed(evt);
            }
        });

        tbAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAccountMouseClicked(evt);
            }
        });
        spAccount.setViewportView(tbAccount);

        btnRemoveAccount.setText("Remove");
        btnRemoveAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAccountActionPerformed(evt);
            }
        });

        btnUpdateAccount.setText("Update");
        btnUpdateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAccountActionPerformed(evt);
            }
        });

        txtIDAccount.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIDAccount.setEnabled(false);

        lbIDAccount.setText("ID:");

        lbNameAccount.setText("Name:");

        lbTypeAccount.setText("Type:");

        btnResetPassword.setText("Reset Password");
        btnResetPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPasswordActionPerformed(evt);
            }
        });

        lbUserAccount.setText("User:");

        javax.swing.GroupLayout tpAccountLayout = new javax.swing.GroupLayout(tpAccount);
        tpAccount.setLayout(tpAccountLayout);
        tpAccountLayout.setHorizontalGroup(
            tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tpAccountLayout.createSequentialGroup()
                        .addComponent(btnAddAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnRemoveAccount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnResetPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tpAccountLayout.createSequentialGroup()
                        .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNameAccount)
                            .addComponent(lbTypeAccount)
                            .addComponent(lbIDAccount)
                            .addComponent(lbUserAccount))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbTypeAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNameAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        tpAccountLayout.setVerticalGroup(
            tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tpAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddAccount)
                        .addComponent(btnUpdateAccount))
                    .addComponent(btnRemoveAccount, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tpAccountLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spAccount, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(tpAccountLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbIDAccount)
                            .addComponent(txtIDAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNameAccount)
                            .addComponent(txtNameAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbUserAccount)
                            .addComponent(txtUserAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(tpAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTypeAccount)
                            .addComponent(cbTypeAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(btnResetPassword)
                        .addGap(73, 73, 73))))
        );

        tpAdmin.addTab("Account", tpAccount);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tpAdmin)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tpAdmin)
                .addContainerGap())
        );

        tpAdmin.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        try {
            getPayedBillListByDate();
        } catch (SQLException | ParseException ex) { }
    }//GEN-LAST:event_btnShowActionPerformed

    private void tbDrinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDrinkMouseClicked
        try {
            ArrayList<JTextField> tfList = new ArrayList<>();
            tfList.add(txtIDDrink);
            tfList.add(txtNameDrink);
            tfList.add(txtPriceDrink);
            setDataBinding(tbDrink, tfList);
            cbIDCategoryDataBinding();
        } catch (SQLException ex) { }
    }//GEN-LAST:event_tbDrinkMouseClicked

    private void tbCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCategoryMouseClicked
        ArrayList<JTextField> tfList = new ArrayList<>();
        tfList.add(txtIDCategory);
        tfList.add(txtNameCategory);
        setDataBinding(tbCategory, tfList);
    }//GEN-LAST:event_tbCategoryMouseClicked

    private void tbTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTableMouseClicked
        ArrayList<JTextField> tfList = new ArrayList<>();
        tfList.add(txtIDTable);
        tfList.add(txtNameTable);
        setDataBinding(tbTable, tfList);
        cbStatusTableDataBinding();
    }//GEN-LAST:event_tbTableMouseClicked

    private void tbAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAccountMouseClicked
        ArrayList<JTextField> tfList = new ArrayList<>();
        tfList.add(txtIDAccount);
        tfList.add(txtUserAccount);
        tfList.add(txtNameAccount);
        setDataBinding(tbAccount, tfList);
        cbTypeAccountDataBinding();
    }//GEN-LAST:event_tbAccountMouseClicked

    private void btnAddDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDrinkActionPerformed
        try {
            dto.category curItem = (dto.category)cbCategoryDrink.getSelectedItem();
            String name = txtNameDrink.getText();
            int idCategory = curItem.getID();
            float price = Float.parseFloat(txtPriceDrink.getText());
            if (drBLL.insertDrink(name, idCategory, price)) {
                JOptionPane.showMessageDialog(this, "Add drink complete!");
                getDrinkList();
            }
            else JOptionPane.showMessageDialog(this, "Fail to add drink!");
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnAddDrinkActionPerformed

    private void btnRemoveDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveDrinkActionPerformed
        try {
            int id = Integer.parseInt(txtIDDrink.getText());
            if (drBLL.deleteDrink(id)) {
                JOptionPane.showMessageDialog(this, "Remove drink complete!");
                getDrinkList();
            }
            else JOptionPane.showMessageDialog(this, "Fail to remove drink!");
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnRemoveDrinkActionPerformed
        
    private void btnUpdateDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDrinkActionPerformed
        try {
            int id = Integer.parseInt(txtIDDrink.getText());
            dto.category curItem = (dto.category)cbCategoryDrink.getSelectedItem();
            String name = txtNameDrink.getText();
            int idCategory = curItem.getID();
            float price = Float.parseFloat(txtPriceDrink.getText());
            if (drBLL.updateDrink(id, name, idCategory, price)) {
                JOptionPane.showMessageDialog(this, "Update drink complete!");
                getDrinkList();
            }
            else JOptionPane.showMessageDialog(this, "Fail to update drink!");
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnUpdateDrinkActionPerformed

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        try {
            String name = txtNameCategory.getText();
            if (cgBLL.insertCategory(name)) {
                JOptionPane.showMessageDialog(this, "Add category complete!");
                getCategoryList();
                getDrinkList();
                getCategoryIntoCB();
            }
            else JOptionPane.showMessageDialog(this, "Fail to add category!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnAddCategoryActionPerformed

    private void btnRemoveCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCategoryActionPerformed
        try {
            int id = Integer.parseInt(txtIDCategory.getText());
            if (cgBLL.deleteCategory(id)) {
                JOptionPane.showMessageDialog(this, "Delete category complete!");
                getCategoryList();
                getDrinkList();
                getCategoryIntoCB();
            }
            else JOptionPane.showMessageDialog(this, "Fail to delete category!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnRemoveCategoryActionPerformed

    private void btnUpdateCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCategoryActionPerformed
        try {
            int id = Integer.parseInt(txtIDCategory.getText());
            String name = txtNameCategory.getText();
            if (cgBLL.updateCategory(id, name)) {
                JOptionPane.showMessageDialog(this, "Update category complete!");
                getCategoryList();
                getDrinkList();
                getCategoryIntoCB();
            }
            else JOptionPane.showMessageDialog(this, "Fail to update category!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnUpdateCategoryActionPerformed

    private void btnSearchDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchDrinkActionPerformed
        try {
            String name = txtSearchDrink.getText();
            tbDrink.removeAll();
            DefaultTableModel tbModel = new DefaultTableModel();
            String colsName[] = {"ID", "Name", "Price", "ID Category"};
            tbModel.setColumnIdentifiers(colsName);
            ArrayList<dto.drink> drinkList = drBLL.searchDrinkByName(name);
            for (dto.drink dr : drinkList) {
                Vector rowData = new Vector();
                rowData.add(Integer.toString(dr.getID()));
                rowData.add(dr.getName());
                rowData.add(Float.toString(dr.getPrice()));
                rowData.add(Integer.toString(dr.getIDCategory()));
                tbModel.addRow(rowData);
            }
            tbDrink.setModel(tbModel);
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnSearchDrinkActionPerformed

    private void btnAddTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTableActionPerformed
        try {
            String name = txtNameTable.getText();
            if (tbBLL.insertTable(name)) {
                JOptionPane.showMessageDialog(this, "Add table complete!");
                getTableList();
            }
            else JOptionPane.showMessageDialog(this, "Fail to add table!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnAddTableActionPerformed

    private void btnRemoveTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTableActionPerformed
        try {
            int id = Integer.parseInt(txtIDTable.getText());
            if (tbBLL.removeTable(id)) {
                JOptionPane.showMessageDialog(this, "Remove table complete!");
                getTableList();
            }
            else JOptionPane.showMessageDialog(this, "Fail to eemove table!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnRemoveTableActionPerformed

    private void btnUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTableActionPerformed
        try {
            int id = Integer.parseInt(txtIDTable.getText());
            String name = txtNameTable.getText();
            String status = (String)cbStatusTable.getSelectedItem();
            if (tbBLL.updateTable(id, name, status)) {
                JOptionPane.showMessageDialog(this, "Update table complete!");
                getTableList();
            }
            else JOptionPane.showMessageDialog(this, "Fail to update table!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnUpdateTableActionPerformed

    private void btnAddAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAccountActionPerformed
        try {
            String userName = txtUserAccount.getText();
            String displayName = txtNameAccount.getText();
            int type = "admin".equals((String)cbTypeAccount.getSelectedItem()) ? 1 : 0; 
            String typeName = (String)cbTypeAccount.getSelectedItem();
            if (accBLL.insertAccount(userName, displayName, type, typeName)) {
                    JOptionPane.showMessageDialog(this, "Add account complete!");
                    getAccountList();
                }
                else JOptionPane.showMessageDialog(this, "Fail to add account!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnAddAccountActionPerformed

    private void btnRemoveAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAccountActionPerformed
        try {
            int id = Integer.parseInt(txtIDAccount.getText());
            if (accBLL.removeAccount(id)) {
                    JOptionPane.showMessageDialog(this, "Remove account complete!");
                    getAccountList();
                }
                else JOptionPane.showMessageDialog(this, "Fail to remove account!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnRemoveAccountActionPerformed

    private void btnUpdateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAccountActionPerformed
        try {
            int id = Integer.parseInt(txtIDAccount.getText());
            String userName = txtUserAccount.getText();
            String displayName = txtNameAccount.getText();
            int type = "admin".equals((String)cbTypeAccount.getSelectedItem()) ? 1 : 0; 
            String typeName = (String)cbTypeAccount.getSelectedItem();
            if (accBLL.updateAccount(id, userName, displayName, type, typeName)) {
                    JOptionPane.showMessageDialog(this, "Update account complete!");
                    getAccountList();
                }
                else JOptionPane.showMessageDialog(this, "Fail to update account!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnUpdateAccountActionPerformed

    private void btnResetPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPasswordActionPerformed
        try {
            int id = Integer.parseInt(txtIDAccount.getText());
            if (accBLL.resetPasswordAccount(id)) {
                    JOptionPane.showMessageDialog(this, "Reset password complete!");
                    getAccountList();
                }
                else JOptionPane.showMessageDialog(this, "Fail to reset password!"); 
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnResetPasswordActionPerformed

    public void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                new admin().setVisible(true);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | SQLException | ParseException ex) { }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAccount;
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnAddDrink;
    private javax.swing.JButton btnAddTable;
    private javax.swing.JButton btnRemoveAccount;
    private javax.swing.JButton btnRemoveCategory;
    private javax.swing.JButton btnRemoveDrink;
    private javax.swing.JButton btnRemoveTable;
    private javax.swing.JButton btnResetPassword;
    private javax.swing.JButton btnSearchDrink;
    private javax.swing.JButton btnShow;
    private javax.swing.JButton btnUpdateAccount;
    private javax.swing.JButton btnUpdateCategory;
    private javax.swing.JButton btnUpdateDrink;
    private javax.swing.JButton btnUpdateTable;
    private javax.swing.JComboBox<dto.category> cbCategoryDrink;
    private javax.swing.JComboBox<String> cbStatusTable;
    private javax.swing.JComboBox<String> cbTypeAccount;
    private javax.swing.JLabel lbCategoryDrink;
    private javax.swing.JLabel lbIDAccount;
    private javax.swing.JLabel lbIDCategory;
    private javax.swing.JLabel lbIDDrink;
    private javax.swing.JLabel lbIDTable;
    private javax.swing.JLabel lbNameAccount;
    private javax.swing.JLabel lbNameCategory;
    private javax.swing.JLabel lbNameDrink;
    private javax.swing.JLabel lbNameTable;
    private javax.swing.JLabel lbPriceDrink;
    private javax.swing.JLabel lbStatusTable;
    private javax.swing.JLabel lbTypeAccount;
    private javax.swing.JLabel lbUserAccount;
    private javax.swing.JPanel pnBill;
    private javax.swing.JPanel pnFromDate;
    private javax.swing.JPanel pnToDate;
    private javax.swing.JScrollPane spAccount;
    private javax.swing.JScrollPane spBill;
    private javax.swing.JScrollPane spCategory;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JScrollPane spTableDrink;
    private javax.swing.JTable tbAccount;
    private javax.swing.JTable tbBill;
    private javax.swing.JTable tbCategory;
    private javax.swing.JTable tbDrink;
    private javax.swing.JTable tbTable;
    private javax.swing.JPanel tpAccount;
    private javax.swing.JTabbedPane tpAdmin;
    private javax.swing.JPanel tpBill;
    private javax.swing.JPanel tpCategory;
    private javax.swing.JPanel tpDrink;
    private javax.swing.JPanel tpTable;
    private javax.swing.JTextField txtIDAccount;
    private javax.swing.JTextField txtIDCategory;
    private javax.swing.JTextField txtIDDrink;
    private javax.swing.JTextField txtIDTable;
    private javax.swing.JTextField txtNameAccount;
    private javax.swing.JTextField txtNameCategory;
    private javax.swing.JTextField txtNameDrink;
    private javax.swing.JTextField txtNameTable;
    private javax.swing.JTextField txtPriceDrink;
    private javax.swing.JTextField txtSearchDrink;
    private javax.swing.JTextField txtUserAccount;
    // End of variables declaration//GEN-END:variables
}
