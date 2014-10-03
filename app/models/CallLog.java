package models;

import com.twilio.sdk.resource.instance.Call;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;
import play.db.jpa.JPAPlugin;
import play.db.jpa.Model;
import utils.DataTableMapper;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by rashok on 9/7/14.
 */

@Entity
@Table(name="call_log")
public class CallLog extends Model {

    @Column(name="ac_id")
    public String accountId;

    public enum CallType {
        USER_REQUEST,
        REPLAY
    }

    @Column(name="sid")
    public String callId;

    public String status;

    public Date created;

    public Date modified;

    public String who;

    @Column(name="caller")
    public String caller;

    public String duration;

    public long delay;

    @Enumerated(EnumType.STRING)
    public CallType type;

    public String input;

    public CallLog() {

    }

    @Override
    public String toString() {
        return "CallLog{" +
                "accountId='" + accountId + '\'' +
                ", callId='" + callId + '\'' +
                ", status='" + status + '\'' +
                ", created=" + created +
                ", who='" + who + '\'' +
                ", caller='" + caller + '\'' +
                ", duration='" + duration + '\'' +
                ", input=" + input +
                '}';
    }

    public static void logCallHistory(Call call, long delay, CallType type) {
        CallLog log = new CallLog();
        log.accountId = call.getAccountSid();
        log.callId = call.getSid();
        log.caller = call.getFrom();
        log.who = call.getTo();
        log.status = call.getStatus();
        log.duration = call.getDuration();
        log.delay = delay;
        log.created = new Date();
        log.modified = new Date();
        log.type = type;
        log.save();
    }

    public static CallLog findByCallSId(String callSid) {
        return CallLog.find("sid = ?", callSid).first();
    }

    public static DataTableModel getCallLogsForDisplay(Pagination pagination) {
        String orderByQuery = "";
        String orderDir = "desc";


        if (pagination.sortDir != null && pagination.sortDir.equals("asc"))
            orderDir = "asc";

        switch (pagination.sortBy) {
            case TwilBuzzConstants.INDEX_TYPE:
                orderByQuery = "type";
                break;
            case TwilBuzzConstants.INDEX_CALL_ID:
                orderByQuery = "sid";
                break;
            case TwilBuzzConstants.INDEX_DELAY:
                orderByQuery = "delay";
                break;
            case TwilBuzzConstants.INDEX_FROM:
                orderByQuery = "caller";
                break;
            case TwilBuzzConstants.INDEX_STATUS:
                orderByQuery = "status";
                break;
            case TwilBuzzConstants.INDEX_TO:
                orderByQuery = "who";
                break;
            case TwilBuzzConstants.INDEX_DURATION:
                orderByQuery = "duration";
                break;
            case TwilBuzzConstants.INDEX_INPUT:
                orderByQuery = "input";
                break;
            case TwilBuzzConstants.INDEX_CREATED:
            default:
                orderByQuery = "created";
                break;
        }

        List<CallLog> callLogList = CallLog.find(" order by " + orderByQuery + " " + orderDir).fetch();
        long totalCount = CallLog.count();

        DataTableMapper mapper = new DataTableMapper();
        DataTableModel result = mapper.getDataTableModelFromCallHistoryList(callLogList);

        result.setiTotalRecords((int) totalCount);

        return result;
    }

}
