package com.smartlifedigital.carpoolbuddy.API.Models.Events;

import com.smartlifedigital.carpoolbuddy.API.Models.Profile;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class ProfileEvent {
    private Profile profile;

    public ProfileEvent(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}
