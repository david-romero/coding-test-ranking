package com.idealista.acceptance

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class) //Cucumber does not support JUnit 5 :( https://cucumber.io/docs/cucumber/api/#junit
@CucumberOptions(plugin = ["pretty", "html:target/features"], glue = ["com.idealista.acceptance"], features = ["classpath:features"], snippets = CucumberOptions.SnippetType.CAMELCASE)
class CucumberRunnerITCase