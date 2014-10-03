package utils;

import models.CallLog;
import models.DataTableModel;
import models.TwilBuzzConstants;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rashok on 9/7/14.
 */
public class DataTableMapper {

    public DataTableModel getDataTableModelFromCallHistoryList (List<CallLog> callLogList) {

        DataTableModel result = new DataTableModel();
        ArrayList aaData = new ArrayList();

        if (callLogList != null) {
            for (CallLog log : callLogList) {
                ArrayList<String> entry = new ArrayList<String>();
                entry.add(TwilBuzzConstants.INDEX_CALL_ID, log.callId);
                entry.add(TwilBuzzConstants.INDEX_FROM, log.caller == null ? "" : log.caller);
                entry.add(TwilBuzzConstants.INDEX_TO, log.who == null ? "" : log.who);
                entry.add(TwilBuzzConstants.INDEX_STATUS, log.status == null ? "" : log.status);
                entry.add(TwilBuzzConstants.INDEX_CREATED, log.created == null ? "" : log.created.toString());
                entry.add(TwilBuzzConstants.INDEX_DURATION, log.duration == null ? "" : log.duration);
                entry.add(TwilBuzzConstants.INDEX_DELAY, String.valueOf(log.delay));
                entry.add(TwilBuzzConstants.INDEX_INPUT, StringUtils.isEmpty(log.input) ? "" : log.input);
                entry.add(TwilBuzzConstants.INDEX_TYPE, log.type.name());
                entry.add(TwilBuzzConstants.INDEX_ID, String.valueOf(log.id));
                aaData.add(entry);
            }
        }
        result.setAaData(aaData);
        return result;
    }
}
