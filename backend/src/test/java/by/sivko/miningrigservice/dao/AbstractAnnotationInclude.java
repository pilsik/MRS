package by.sivko.miningrigservice.dao;

import by.sivko.miningrigservice.MiningRigServiceApplication;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-test.properties")
@TestExecutionListeners(DbUnitTestExecutionListener.class)
@ContextConfiguration(classes = MiningRigServiceApplication.class)
@DirtiesContext
public abstract class AbstractAnnotationInclude {
}
