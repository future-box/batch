package com.batch.sample.skip;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SkippableExceptionDuringProcessSample {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6)) {
            @Override
            public Integer read() {
                Integer item = super.read();
                System.out.println("reading item = " + item);
                return item;
            }
        };
    }

    @Bean
    public ItemProcessor<Integer, Integer> itemProcessor() {
        return item -> {
            if (item.equals(5)) {
                System.out.println("Throwing exception on item " + item);
                throw new IllegalArgumentException("Unable to process 5");
            }
            System.out.println("processing item = " + item);
            return item;
        };
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return items -> {
            System.out.println("About to write chunk: " + items);
            for (Integer item : items) {
                System.out.println("writing item = " + item);
            }
        };
    }

    @Bean
    public Step step() {
        return this.stepBuilderFactory.get("step")
            .<Integer, Integer>chunk(3)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .faultTolerant()
            .skip(IllegalArgumentException.class)
            .skipLimit(3)
            .build();
    }

    @Bean
    Job job() {
        return jobBuilderFactory.get("skip-sample")
            .start(step())
            .build();
    }
}
