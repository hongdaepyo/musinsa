package com.dphong.musinsa;

import com.dphong.musinsa.common.config.JpaConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Import(JpaConfig.class)
@DataJpaTest
@Transactional
public abstract class RepositoryTest {

}
