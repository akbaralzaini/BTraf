package project.akbaralzaini.btraf.models.tripscheduleReq;

import com.google.gson.annotations.SerializedName;

public class TripScheduleReq {
    @SerializedName("destStopId")
    private Integer destStopId;
    @SerializedName("sourceStopId")
    private Integer sourceStopId;

    public TripScheduleReq(Integer destStopId, Integer sourceStopId) {
        this.destStopId = destStopId;
        this.sourceStopId = sourceStopId;
    }

    public TripScheduleReq() {
    }

    public Integer getDestStopId() {
        return destStopId;
    }

    public void setDestStopId(Integer destStopId) {
        this.destStopId = destStopId;
    }

    public Integer getSourceStopId() {
        return sourceStopId;
    }

    public void setSourceStopId(Integer sourceStopId) {
        this.sourceStopId = sourceStopId;
    }
}
