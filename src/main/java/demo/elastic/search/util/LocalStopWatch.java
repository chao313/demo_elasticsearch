package demo.elastic.search.util;

import org.springframework.util.StopWatch;

import java.text.NumberFormat;

public class LocalStopWatch extends StopWatch {

    public LocalStopWatch(String id) {
        super(id);
    }

    public LocalStopWatch() {
    }

    /**
     * Generate a string with a table describing all tasks performed.
     * <p>For custom reporting, call {@link #getTaskInfo()} and use the task info
     * directly.
     */
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        sb.append("---------------------------------------------\n");
        sb.append("ns         %     Task name\n");
        sb.append("---------------------------------------------\n");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(9);
        nf.setGroupingUsed(false);
        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);
        for (TaskInfo task : getTaskInfo()) {
            sb.append(nf.format(task.getTimeMillis())).append("  ");
            sb.append(pf.format((double) task.getTimeMillis() / getTotalTimeMillis())).append("  ");
            sb.append(task.getTaskName()).append("\n");
        }
        return sb.toString();
    }

}
