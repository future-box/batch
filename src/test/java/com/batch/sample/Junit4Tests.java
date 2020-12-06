package com.batch.sample;

import com.batch.sample.skip.SkippableExceptionDuringProcessSample;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SkippableExceptionDuringProcessSample.class})
public class Junit4Tests {


}
