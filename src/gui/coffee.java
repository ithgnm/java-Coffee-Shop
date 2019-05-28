
package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public final class coffee extends javax.swing.JFrame {
    
    private dto.account curAccount;

    public dto.account getCurAccount() {
        return curAccount;
    }
    
    public void setCurAccount(dto.account curAccount) {
        this.curAccount = curAccount;
    }
    
    public coffee(dto.account account) throws SQLException {
        initComponents();
        this.curAccount = account;
        getAccountInformationIntoMenuBar();
        getTableList();
        getCategoryIntoCB();
        getDrinkIntoCB(1);
        getTableIntoCB();
    }
    
    public void getAccountInformationIntoMenuBar() {
        String accountName = "Information" + " (" + curAccount.getDisplayName() + ")";
        mbInformation.setText(accountName);
        boolean type = curAccount.getType() == 1;
        mbAdmin.setEnabled(type);
    }
    
    public void getTableList() throws SQLException {
        pnTable.removeAll();
        javax.swing.GroupLayout pnTableLayout = new javax.swing.GroupLayout(pnTable);
        pnTable.setLayout(pnTableLayout);
        pnTableLayout.setHorizontalGroup(
                pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 488, Short.MAX_VALUE)
        );
        pnTableLayout.setVerticalGroup(
                pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 405, Short.MAX_VALUE)
        );
        pnTable.setLayout(new GridLayout(4, 7, 7, 7));
        bll.tableBLL tbBLL = new bll.tableBLL();
        ArrayList<dto.table> tableList = tbBLL.getTableList();
        int tableRows = tableList.size()%3 == 0 ? tableList.size()/3 : (tableList.size()/3 + 1);
        pnTable.setLayout(new GridLayout(tableRows, 7, 7, 7));
        for (dto.table tb : tableList) {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(dal.tableDAL.tbWidth, dal.tableDAL.tbHeight));
            btn.setText("<html>" + tb.getName() + "<br>" + tb.getStatus() + "</html>");
            btn.setBorder(new LineBorder(Color.lightGray));
            btn.setBackground(Color.getHSBColor(200, 250, 150));
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.addActionListener((ActionEvent e) -> {
                try {
                    txtCurrentTable.setText(tb.getName());
                    getMenuListByIDTable(tb.getID());
                } catch (SQLException ex) { }
            });
            switch (tb.getStatus()) {
                case "Empty": break;
                default: btn.setBackground(Color.getHSBColor(10, 0.25f, 1f));
            }
            pnTable.add(btn);
        }
    }
    
    public void getMenuListByIDTable(int id) throws SQLException {
        tbMenu.removeAll();
        DefaultTableModel tbModel = new DefaultTableModel();
        String colsName[] = {"Drink Name", "Count", "Price", "Total"};
        tbModel.setColumnIdentifiers(colsName);
        bll.menuBLL mnBLL = new bll.menuBLL();
        float totalPrice = 0;
        ArrayList<dto.menu> menuList = mnBLL.getMenuListByIDTable(id);
        for (dto.menu mn : menuList) {
            Vector rowData = new Vector();
            rowData.add(mn.getDrinkName());
            rowData.add(mn.getCount());
            rowData.add(mn.getPrice());
            rowData.add(mn.getTotalPrice());
            totalPrice += mn.getTotalPrice();    
            tbModel.addRow(rowData);
        }
        tbMenu.setName(Integer.toString(id));
        tbMenu.setModel(tbModel);
        txtTotalPrice.setText(Float.toString(totalPrice) + " đ");
    }
    
    public void getCategoryIntoCB() throws SQLException {
        bll.categoryBLL cgBLL = new bll.categoryBLL();
        ArrayList<dto.category> categoryList = cgBLL.getCategoryList();
        cbCategory.setModel(new DefaultComboBoxModel<>(categoryList.toArray(new dto.category[0])));
        cbCategory.addItemListener((ItemEvent e) -> {
            try {
                dto.category curItem = (dto.category)cbCategory.getSelectedItem();
                getDrinkIntoCB(curItem.getID());
            } catch (SQLException ex) { }
        });
    }
    
    public void getDrinkIntoCB(int id) throws SQLException {
        bll.drinkBLL drBLL = new bll.drinkBLL();
        ArrayList<dto.drink> drinkList = drBLL.getDrinkByIDCategory(id);
        cbDrink.setModel(new DefaultComboBoxModel<>(drinkList.toArray(new dto.drink[0])));
    }
    
    public void getTableIntoCB() throws SQLException {
        bll.tableBLL tbBLL = new bll.tableBLL();
        ArrayList<dto.table> tableList = tbBLL.getTableList();
        cbSwitchTable.setModel(new DefaultComboBoxModel<>(tableList.toArray(new dto.table[0])));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnTable = new javax.swing.JPanel();
        lbAuthor = new javax.swing.JLabel();
        lbProject = new javax.swing.JLabel();
        pnManager = new javax.swing.JPanel();
        cbCategory = new javax.swing.JComboBox<>();
        cbDrink = new javax.swing.JComboBox<>();
        snDrinkCount = new javax.swing.JSpinner();
        btnAddDrink = new javax.swing.JButton();
        spMenu = new javax.swing.JScrollPane();
        tbMenu = new javax.swing.JTable();
        btnSwitchTable = new javax.swing.JButton();
        btnPayment = new javax.swing.JButton();
        btnDiscount = new javax.swing.JButton();
        cbSwitchTable = new javax.swing.JComboBox<>();
        snDiscount = new javax.swing.JSpinner();
        txtTotalPrice = new javax.swing.JTextField();
        txtCurrentTable = new javax.swing.JTextField();
        mbCoffee = new javax.swing.JMenuBar();
        mbAdmin = new javax.swing.JMenu();
        mbManagement = new javax.swing.JMenuItem();
        mbInformation = new javax.swing.JMenu();
        mbUserInfomation = new javax.swing.JMenuItem();
        mbLogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Coffee Manager");
        setIconImages(null);
        setName(""); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(816, 525));

        javax.swing.GroupLayout pnTableLayout = new javax.swing.GroupLayout(pnTable);
        pnTable.setLayout(pnTableLayout);
        pnTableLayout.setHorizontalGroup(
            pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );
        pnTableLayout.setVerticalGroup(
            pnTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        lbAuthor.setForeground(new java.awt.Color(102, 102, 102));
        lbAuthor.setText("Huy Nguyen Minh - 175050002");

        lbProject.setForeground(new java.awt.Color(102, 102, 102));
        lbProject.setText("Java Development Project");
        lbProject.setToolTipText("");

        cbCategory.setPreferredSize(new java.awt.Dimension(121, 21));

        cbDrink.setPreferredSize(new java.awt.Dimension(121, 21));

        snDrinkCount.setModel(new javax.swing.SpinnerNumberModel(1, -10, 10, 1));
        snDrinkCount.setMinimumSize(new java.awt.Dimension(63, 21));
        snDrinkCount.setPreferredSize(new java.awt.Dimension(63, 21));

        btnAddDrink.setText("Add Drink");
        btnAddDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDrinkActionPerformed(evt);
            }
        });

        tbMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        spMenu.setViewportView(tbMenu);

        btnSwitchTable.setText("Switch");
        btnSwitchTable.setName(""); // NOI18N
        btnSwitchTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwitchTableActionPerformed(evt);
            }
        });

        btnPayment.setText("Payment");
        btnPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentActionPerformed(evt);
            }
        });

        btnDiscount.setText("Discount");
        btnDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiscountActionPerformed(evt);
            }
        });

        cbSwitchTable.setPreferredSize(new java.awt.Dimension(58, 21));

        snDiscount.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        snDiscount.setMinimumSize(new java.awt.Dimension(63, 21));
        snDiscount.setPreferredSize(new java.awt.Dimension(63, 21));

        txtTotalPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalPrice.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalPrice.setEnabled(false);
        txtTotalPrice.setPreferredSize(new java.awt.Dimension(61, 21));

        txtCurrentTable.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCurrentTable.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCurrentTable.setEnabled(false);

        javax.swing.GroupLayout pnManagerLayout = new javax.swing.GroupLayout(pnManager);
        pnManager.setLayout(pnManagerLayout);
        pnManagerLayout.setHorizontalGroup(
            pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnManagerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbSwitchTable, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSwitchTable, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(snDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(spMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnManagerLayout.createSequentialGroup()
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnManagerLayout.createSequentialGroup()
                        .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCurrentTable))
                    .addGroup(pnManagerLayout.createSequentialGroup()
                        .addComponent(cbDrink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddDrink, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(snDrinkCount, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnManagerLayout.setVerticalGroup(
            pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnManagerLayout.createSequentialGroup()
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCurrentTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDrink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snDrinkCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddDrink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSwitchTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnManagerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSwitchTable)
                    .addComponent(btnPayment)
                    .addComponent(btnDiscount)))
        );

        mbAdmin.setText("Admin");

        mbManagement.setText("Management");
        mbManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbManagementActionPerformed(evt);
            }
        });
        mbAdmin.add(mbManagement);

        mbCoffee.add(mbAdmin);

        mbInformation.setText("Information");

        mbUserInfomation.setText("User Information");
        mbUserInfomation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbUserInfomationActionPerformed(evt);
            }
        });
        mbInformation.add(mbUserInfomation);

        mbLogout.setText("Logout");
        mbLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbLogoutActionPerformed(evt);
            }
        });
        mbInformation.add(mbLogout);

        mbCoffee.add(mbInformation);

        setJMenuBar(mbCoffee);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbAuthor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbProject))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnManager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnManager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAuthor)
                    .addComponent(lbProject))
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mbLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbLogoutActionPerformed
        this.setVisible(false);
        login lg = new login();
        lg.setLocationRelativeTo(null);
        lg.setVisible(true);
    }//GEN-LAST:event_mbLogoutActionPerformed

    private void btnAddDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDrinkActionPerformed
        try {
            if (tbMenu.getName() == null) {
                JOptionPane.showMessageDialog(this, "Choose table!");
                return;
            }
            dto.drink curDrink = (dto.drink) cbDrink.getSelectedItem();
            bll.billBLL bBLL = new bll.billBLL();
            bll.billinfoBLL biBLL = new bll.billinfoBLL();
            int iDTable = Integer.parseInt(tbMenu.getName());
            int iDBill = bBLL.getUncheckBillByIDTable(iDTable);
            int iDDrink = curDrink.getID();
            int count = (int)snDrinkCount.getValue();
            if (iDBill == -1) {
                bBLL.insertBillIntoTable(iDTable);
                biBLL.insertBillInfo(bBLL.getLatestIDBill(), iDDrink, count);
            }
            else {
                biBLL.insertBillInfo(iDBill, iDDrink, count);
            }
            getMenuListByIDTable(iDTable);
            getTableList();
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnAddDrinkActionPerformed

    private void btnPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentActionPerformed
        try {
            if (tbMenu.getName() == null) {
                JOptionPane.showMessageDialog(this, "Choose table!");
                return;
            }
            bll.billBLL bBLL = new bll.billBLL();
            int iDTable = Integer.parseInt(tbMenu.getName());
            int iDBill = bBLL.getUncheckBillByIDTable(iDTable);
            int discount  = (int)snDiscount.getValue();
            double totalPrice = Double.parseDouble(txtTotalPrice.getText().split(" ")[0]);
            if (iDBill != -1) {
                if (JOptionPane.showConfirmDialog(null, "Payment?", "", JOptionPane.OK_CANCEL_OPTION) == 0) {
                    bBLL.updatePayedBill(iDBill, discount, (float)totalPrice);
                    getMenuListByIDTable(iDTable);
                    getTableList();
                }
            }
        } catch (SQLException ex) { }
    }//GEN-LAST:event_btnPaymentActionPerformed

    private void btnDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscountActionPerformed
        if (tbMenu.getName() == null) {
            JOptionPane.showMessageDialog(this, "Choose table!");
            return;
        }
        float price = Float.parseFloat(txtTotalPrice.getText().split(" ")[0]);
        float discount = 1 - ((int)(snDiscount.getValue())*0.01f);
        txtTotalPrice.setText(Float.toString(price * discount) + " đ");
    }//GEN-LAST:event_btnDiscountActionPerformed

    private void btnSwitchTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwitchTableActionPerformed
        if (tbMenu.getName() == null) {
            JOptionPane.showMessageDialog(this, "Choose table!");
            return;
        }
        int iDCurTable = Integer.parseInt(tbMenu.getName());
        dto.table sltTable = (dto.table) cbSwitchTable.getSelectedItem();
        int iDSltTable = sltTable.getID();
        if (JOptionPane.showConfirmDialog(null, "Switch table?", "", JOptionPane.OK_CANCEL_OPTION) == 0) {
            try {
                bll.tableBLL tbBLL = new bll.tableBLL();
                tbBLL.switchTable(iDCurTable, iDSltTable);
                getTableList();
                getMenuListByIDTable(iDCurTable);
            } catch (SQLException ex) { }
        }
    }//GEN-LAST:event_btnSwitchTableActionPerformed

    private void mbUserInfomationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbUserInfomationActionPerformed
        profile pf = new profile(curAccount);
        pf.setLocationRelativeTo(null);
        pf.setVisible(true);
    }//GEN-LAST:event_mbUserInfomationActionPerformed

    private void mbManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbManagementActionPerformed
        try {
            admin ad = new admin();
            ad.setLocationRelativeTo(null);
            ad.setVisible(true);
            ad.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent event) {
                    try {
                        getAccountInformationIntoMenuBar();
                        getTableList();
                        getCategoryIntoCB();
                        getDrinkIntoCB(1);
                        getTableIntoCB();
                    } catch (SQLException ex) { }
                }
            });
        } catch (SQLException | ParseException ex) { }
    }//GEN-LAST:event_mbManagementActionPerformed
    
    public void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                new coffee(curAccount).setVisible(true);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | SQLException  ex) { }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDrink;
    private javax.swing.JButton btnDiscount;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnSwitchTable;
    private javax.swing.JComboBox<dto.category> cbCategory;
    private javax.swing.JComboBox<dto.drink> cbDrink;
    private javax.swing.JComboBox<dto.table> cbSwitchTable;
    private javax.swing.JLabel lbAuthor;
    private javax.swing.JLabel lbProject;
    private javax.swing.JMenu mbAdmin;
    private javax.swing.JMenuBar mbCoffee;
    private javax.swing.JMenu mbInformation;
    private javax.swing.JMenuItem mbLogout;
    private javax.swing.JMenuItem mbManagement;
    private javax.swing.JMenuItem mbUserInfomation;
    private javax.swing.JPanel pnManager;
    private javax.swing.JPanel pnTable;
    private javax.swing.JSpinner snDiscount;
    private javax.swing.JSpinner snDrinkCount;
    private javax.swing.JScrollPane spMenu;
    private javax.swing.JTable tbMenu;
    private javax.swing.JTextField txtCurrentTable;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables
}