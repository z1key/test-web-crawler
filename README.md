## Application overview

Application gets two pages (origin & diff) and id of element from origin and tries to find similar element on different page.
If it finds the element with same id it returns it immediately. Otherwise it tries to find tag with attributes that match with origin's.
The implementation is based on Priority Queue. That means that element with more equal attributes will be the greatest candidate.

## Program run

java -jar */html-crawler-1.0-SNAPSHOT.jar <origin_file_path> <diff_file_path> <element_id>
