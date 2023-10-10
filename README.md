# Lead Management System

## Table of Contents

1. [Introduction](#introduction)<br>
    1.1 [Purpose](#purpose)<br>
    1.2 [Scope](#scope)
2. [Functional Requirements](#functional-requirements)<br>
    2.1 [Lead Management](#lead-management)<br>
    2.2 [Lead Stages](#lead-stages)<br>
    2.3 [Forms](#forms)<br>
    2.4 [Dashboards](#dashboards)<br>
    2.5 [Reports](#reports)
3. [Non-Functional Requirements](#non-functional-requirements)<br>
    3.1 [Performance](#performance)<br>
    3.2 [Security](#security)<br>
    3.3 [Usability](#usability)<br>
    3.4 [Compatibility](#compatibility)
4. [Technical Details](#technical-details)
5. [Testing and Test Strategy](#testing-and-test-strategy)<br>
    5.1 [Unit Testing](#unit-testing)<br>
    5.2 [Integration Testing](#integration-testing)<br>
    5.3 [User Acceptance Testing](#user-acceptance-testing)<br>
6. [References](#references)
7. [Conclusion](#conclusion)

## Introduction

### Purpose

The purpose of this document is to outline the software requirements for the development of a Lead Management System for Sugar CRM. It provides a comprehensive overview of the project's objectives, functionality, and technical specifications.

### Scope

The Lead Management System will encompass lead management through five stages, including the creation of various forms, dashboards, and reports. The system will be developed using a full-stack technology stack.

## Functional Requirements

### Lead Management

- The system shall allow the creation, modification, and deletion of leads.
- Leads shall be categorized into five stages: New, Qualified, Proposition, Negotiation, and Won.
- Users shall be able to assign leads to specific team members.
- Leads shall contain relevant information, including contact details, source, and notes.

### Lead Stages

- New: Leads that have been newly acquired and require qualification.
- Qualified: Leads that have been assessed and deemed worthy of further pursuit.
- Proposition: Leads that have received proposals or offers.
- Negotiation: Leads in the negotiation phase with potential clients.
- Won: Leads that have been successfully converted into customers.

### Forms

- The system shall provide the capability to create web-based forms for lead capture.
- Forms shall be customizable, allowing for the inclusion of specific fields.
- Mobile app-based forms can be developed for lead capture on the go.
- Chat-based forms for lead capture via chat applications can be implemented.

### Dashboards

- Users shall have access to customizable dashboards that display key lead management metrics.
- Dashboards shall provide real-time insights into lead progression.
- Managers shall be able to view team-specific dashboards for lead tracking.

### Reports

- The system shall generate detailed reports on lead performance.
- Reports shall include lead conversion rates, stage-wise distribution, and source analysis.
- Users shall have the option to export reports in various formats (e.g., PDF, Excel).

## Non-Functional Requirements

### Performance

- The system shall respond to user interactions fast.
- The system shall be designed to handle many users without significant performance degradation.

### Security

- Users shall be required to authenticate using secure methods (e.g., username and password, multi-factor authentication).
- Data transmission and storage shall be encrypted to protect sensitive information.
- Role-based access control (RBAC) shall be implemented to restrict access to sensitive data and features.

### Usability

- The user interface shall be intuitive and user-friendly to enhance user adoption.

### Compatibility

- The system shall function consistently across popular web browsers (e.g., Chrome, Firefox, Safari).

## Technical Details

The technologies used for this project are as follows:

- Frontend Framework: Angular
- Backend Framework: Spring Boot
- Database Management: MySQL

## Testing and Test Strategy

### Unit Testing

Individual components and functions of the system will be tested in isolation to verify their correctness.

### Integration Testing

Modules and components will be tested together to ensure they interact correctly and pass data seamlessly.

### User Acceptance Testing

End-users will participate in UAT to validate that the system meets their needs and expectations.

## References

[Odoo CRM](https://www.odoo.com/app/crm?utm_source=google&utm_medium=cpc&utm_campaign=IN-EN-CRM&utm_term=free%20crmutm_gclid=CjwKCAjwo9unBhBTEiwAip
