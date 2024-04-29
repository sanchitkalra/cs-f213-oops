package com.oops.csf213.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// this will hold the data for our runs in memory (now in H2 DB)
// we are basically following the repository pattern for accessing data
@org.springframework.stereotype.Repository // now this class is in the Spring context and is managed by the Spring framework
public class RunRepository {
    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return this.jdbcClient
                .sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findByID(Integer id) {
        return this.jdbcClient
                .sql("select id, title, started_on, completed_on, miles, location from run where id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

     public void create(Run run) {
        var updated = this.jdbcClient
                .sql("insert into Run(id, title, started_on, completed_on, miles, location) values (?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();

         Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        var updated = this.jdbcClient
                .sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update " + run.title());
    }

    public void delete(Integer id) {
        var updated = this.jdbcClient
                .sql("delete from run where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    public void saveAll(List<Run> runs) {
        runs.stream().forEach(this::create);
    }

    public int count() {
        return this.jdbcClient.sql("select * from run").query().listOfRows().size();
    }

/*
 Commenting this out to build a Database Repository
    private List<Run> runs = new ArrayList<Run>();

    // we can use some annotations in the lifecycle of construction of a class
    @PostConstruct // used on a method that needs to be exec after dependency injection is done for init purposes
    private void init() {
        runs.add(new Run(1, "", LocalDateTime.now(), LocalDateTime.now(), 10, Location.INDOOR));
    }

    List<Run> findAll() {
        return runs;
    }

    // we're making this an optional
    // optionals may contain a non-null value. check for value presence with isPresent()
    Optional<Run> findByID(int Id) {
        return runs
                .stream()
                .filter(run -> run.id() == Id)
                .findFirst();
    }

    void create(Run run) {
        runs.add(run);
    }

    void update(Run run, Integer id) {
        Optional<Run> existingRun = findByID(id);
        existingRun.ifPresent(value -> runs.set(runs.indexOf((value)), run));
    }

    void delete(Integer id) {
        runs.removeIf(run -> run.id().equals(id));
    }
*/
}
