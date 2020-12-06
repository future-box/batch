package com.batch.sample;

import com.batch.sample.skip.SkippableExceptionDuringProcessSample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SkippableExceptionDuringProcessSample.class, JobRunnerConfiguration.class})
class BatchApplicationTests {

    @Autowired
    private JobLauncherTestUtils utils;

    @Test
    void test() throws Exception {
        utils.launchJob();
    }
}
