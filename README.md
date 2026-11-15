# CICD-1 Coding Test — Review, Improve and Reflect

**Student ID: G00470377 
**Examination group:** A (Room Booking / JPQL) 
**Original examination pull-request URL (`exam-work`): https://github.com/ArlenATU/G00470377-atu.ie/tree/model.booking
**New review repository URL: https://github.com/ArlenATU/CICD_Review

## 1. My original examination self-assessment

Review the code **as submitted in the original examination**. Do not change the original repository, branch or pull request. Check your own Group A or Group B examination paper and award marks only for evidence present in the original submission.

### Task 1 — /40
**My mark: 15
**Evidence from the original code (files, methods, working or missing requirements):  
I've created booking entity
@Entity
@Data
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String OrganiserName;
    private int count;
    private String status;
    private String RoomName;
}


### Task 2 — /40
**My mark: 0
**Evidence from the original code:**  

### Task 3 — /20
**My mark: 0
**Evidence from the original code:**  

**Total self-assessed original examination mark: 15/100

**Checkpoint:** Commit this completed self-assessment as the **first review commit in your new repository**, before you modify or add application code. The lecturer decides the official original examination mark.

## 2. Corrections and improvements in my new project
**Problem and correction 1:**  
In the exam, I only make a simple Booking entity. I forgot to make DTOs and validation, so bad data could enter.
Created BookingRequest and BookingResponse classes and added validation rules like @NotBlank and @Min.
DTOs keep the database safe, and validation catches bad input before it goes to the database.
**Problem and correction 2:**  
Task 2 got 0 marks because I did not write controller methods and did not check if room capacity is enough
Added BookingController with POST and GET methods, and service logic to check room capacity.
It stops users from booking when too many people try to fit in a small room, and gives correct error codes.
**Problem and correction 3 (optional):**  

## 3. API testing and evidence

Use Swagger UI or another HTTP client. For **at least four meaningful checks**, record the request/input, expected result, actual HTTP status/response, and pass/fail. Include unsuccessful cases; a running application alone is not enough.

**Test 1 — successful booking request:**  
- Request / input:  {
  "count": 18,
  "organiserName": "Aofie Byrne",
  "roomName": "Lab 201"
  }
- Expected: 201 Created
- Actual HTTP status / body: {
  "timestamp": "2026-11-15T22:02:31.437Z",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/bookings"
  } 
- Pass / fail:  fail

**Test 2 — invalid input or excessive attendee count:**  
- Request / input:  {
- "roomName": "Meeting Room 3", "organiserName": "John", "count": 25
- }
- Expected: 400 Bad Request 
- Actual HTTP status / body: {
- "error": "Attendee count (25) exceeds room capacity (10)"
- }
- Pass / fail:  Pass

**Test 3 — missing booking or room:**  
- Request / input:  GET /api/bookings/999
- Expected:  404 Not Found
- Actual HTTP status / body:  {
- "error": "Booking not found with id: 999"
- }
- Pass / fail:  Pass

**Test 4 — requirement specific to my group:**  
- Request / input: Lab 201
- Expected:  200 Ok
- Actual HTTP status / body:  
- Pass / fail:  fail

**Group A:** Demonstrate `GET /api/bookings/search?roomName=...` using an exact room name, bookings ordered by attendee count descending, and an empty list for no matches. Add checks if necessary to show all three behaviours.

**Group B:** Run the **provided** Room Service separately on port 8082 and the **new** Booking Service on port 8081. Demonstrate that booking creation obtains room details through OpenFeign and that booking retrieval uses the local Booking database. Do not modify the Room Service.

## 4. Reflection

I understood how the models and database logic needed to work in my head, but during the exam I got stressed and forgot how to write the DTOs, validation, and controller methods. 

**Which Spring Boot, JPA, validation, JPQL or OpenFeign concept became clearer after correcting my code? Give a specific example.**  
JPQL query became much clearer. For example, writing SELECT b FROM Booking b WHERE b.RoomName = :roomName ORDER BY b.count DESC made it easy to query and sort data directly from the repository without writing manual sorting logic in Java.
**What will I do differently in my next coding test or API development task?**  
Next time, I will build the project step by step. I will create the entity first, test simple requests in Swagger early, and not leave controller mapping and search queries until the very end.
## Review lab assessment — 20 marks

- Honest original self-assessment with code evidence: **4 marks**
- Explanation of mistakes and Spring Boot concepts: **6 marks**
- Corrections in the new application: **4 marks**
- API testing and demonstration of understanding: **4 marks**
- Reflection and future improvement: **2 marks**

**Total: 20 marks.** Simply submitting a working solution does not demonstrate the learning required by this review.

## Proposed mark recovery — only if confirmed for CICD-1

If the same approach as OOP is adopted, the review could recover **up to 30% of marks lost** in the original examination, scaled by the review-lab mark.

**Marks recovered = (100 − official original exam mark) × 0.30 × (review lab mark ÷ 20)**

Example: exam **40/100** and review **16/20** → **14.4 recovered marks**, revised exam **54.4/100**. This is **not an approved CICD-1 policy unless confirmed by the lecturer**. The original official exam mark—not your self-awarded grade—is used in the calculation.

## Submission

Commit and push the **new review repository** with the completed README, corrected Spring Boot code and API-test evidence. Submit its GitHub URL on Moodle. **Do not push any changes to the original examination repository or `exam-work` pull request.**
