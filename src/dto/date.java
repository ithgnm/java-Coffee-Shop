
package dto;

import java.text.*;
import java.util.*;
import javax.swing.JFormattedTextField.*;

public class date extends AbstractFormatter {
    
    private String datePattern = "MM dd, yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
