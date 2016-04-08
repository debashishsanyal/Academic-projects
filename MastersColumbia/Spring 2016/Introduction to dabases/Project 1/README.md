# Introduction to Databases project:
w4111: Introduction to Databases - Evan Jones, Columbia University

Spring 2016

This application is a search platform that allows users to find Academicians, Researchers, Publication, Conferences or Journals based on multiple search criteria. PostgreSQL database. Python with Flask for the back end.


Install the following:

$sudo apt-get install postgresql-9.3 postgresql-server-dev-9.3 sqlite3 git python-virtualenv python-dev

Create a virtual environment:

$mkvirtualenv env1

Switch to virtual env:

$workon env

Install some more dependencies

$pip install flask, psycopg2, sqlalchemy, click

To run locally

$python server.py

Go to http://localhost:8111 in your browser

Deactivate environment when done:
$deactivate
