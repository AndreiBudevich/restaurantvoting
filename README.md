## Java Enterprise Restaurant Voting 

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e9202d586ac04c6481a15f031e957641)](https://www.codacy.com/gh/AndreiBudevich/restaurantvoting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=AndreiBudevich/restaurantvoting&amp;utm_campaign=Badge_Grade)

-----------------------------

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

-----------------------------
