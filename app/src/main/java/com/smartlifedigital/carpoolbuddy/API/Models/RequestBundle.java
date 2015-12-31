package com.smartlifedigital.carpoolbuddy.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class RequestBundle {
    @SerializedName("requester_id")
    @Expose
    private long requesterId;

    @SerializedName("target_id")
    @Expose
    private long targetId;

    public long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
}
