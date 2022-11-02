# Technical Task (Software Engineer)

Take as long as you need - as a guideline we would expect it to take up to 60 minutes.

## Introduction

[Glean](https://glean.co/) lets you record audio in an **Event**. Within an Event you can create **Notes**. A simplified version of the Event
record is as follows:

<pre>
+-------------------------------+
|             Event             |
+-------------------------------+
| name  | Text                  | 
| notes | A collection of Notes | 
+-------------------------------+
</pre>

<pre>
+------------------+
|       Note       |
+------------------+
| id   | Unique ID | 
| text | Text      | 
+------------------+
</pre>

The Event can be saved to the backend and synced to other clients. This brings the possibility of two different
clients (e.g. the web application and a mobile app) making changes to the same Event and having to resolve the
differences.

## Task

Implement code to handle two input Events, one remote and one local, outputting a combined Event. For example, a function with signature `resolveEvents(remote: Event, local: Event): Event`, where:

- **remote**: the Event that was last uploaded to the backend.
- **local**: the copy of the Event with local changes.
- **return:** resolved version of the Event, combining the information from both inputs.

Requirements/assumptions:
- Notes in the two events may or may not share IDs - you should handle both cases.
- All input data is guaranteed not to be null.
- No data from either input should be lost in the merge. Avoid unnecessary duplication.
- Combine conflicting string data by separating with a slash (see the example).
- Write unit tests that cover the logic of your solution.

## Example
The examples below are given in JSON-like syntax, but it's not necessary to implement JSON serialisation/deserialisation.

### Local Event
```
{
  name: 'Name 1',
  notes: [
    { id: 1, text: 'A' }
  ]
}
```
### Remote Event
```
{
  name: 'Name 2',
  notes: [
   { id: 2, text: 'B' }
  ]
}
```
### Resolved Event
```
{
  name: 'Name 1 / Name 2',
  notes: [
   { id: 1, text: 'A' },
   { id: 2, text: 'B' }
  ]
}
```
## Submit your solution

Please push your solution to a branch and open a PR once you are finished. \
Get in touch to let us know you are done and we'll aim to review your code within 2 working days.
