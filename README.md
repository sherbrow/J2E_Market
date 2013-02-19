J2E_Market
==========

Small project built as testing project for a J2E course

# Developer Guide

Just browse the code a bit, especially the [`web`](src/java/web) namespace.

You could find a few tricks by browsing the JSF files too ([`/web/jsf/`](web/jsf)).

# Improvements

- Hard or soft proxy on the HttpRequest (and response) that the action can access
- Controller actions should not be required to generate their own ViewBags (although it should still be possible)
- Controller actions should **definitely** generate their answer via a factory
