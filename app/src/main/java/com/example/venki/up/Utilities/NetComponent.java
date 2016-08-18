package com.example.venki.up.Utilities;

import com.example.venki.up.activities.jobs.JobsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by samsiu on 8/17/16.
 */
@Singleton
@Component(modules={NetModule.class})
public interface NetComponent {
    void inject(JobsActivity activity);
}
