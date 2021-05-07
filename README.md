Project Setup
- Dependeny lib jars are inside the lib folder.
- In an IDE, should be able to run the AffirmApp class (it has the main method)


1. How long did you spend working on the problem? What did you find to be the most
   difficult part? 
   
- I spent close to 5 hours to get a solution working and used an additional 1.5 hour time to clean up.  This was definitely a great experience for me and I thoroughly enjoyed my time trying to get a working output.  Covenants and yields were a bit tricky for me.  Should have had unit testing as well but ran out of time.

2. How would you modify your data model or code to account for an eventual introduction
   of new, as-of-yet unknown types of covenants, beyond just maximum default likelihood
   and state restrictions?
    
- The beauty of development is that you can continously try to refine it to be better and more optimal than what it is now.  I would externalize the covenants (as they are business rules) in either a config file or a database.  Depending on how often they change, perhaps provide an easy way for a super user to modify the covenants through a UI or a rules engine tool without having the need to redeploy. Covenant can also be stood up as a separate microservice further decoupling it from the main application code.

3. How would you architect your solution as a production service wherein new facilities can
   be introduced at arbitrary points in time. Assume these facilities become available by the
   finance team emailing your team and describing the addition with a new set of CSVs.

- Perhaps have the facilities be loaded from CSVs into a key-value document store and that can be in a cache such as Redis where the look up will be relatively fast compared to a db lookup.  We can also make the business logic of new loans that are streamed in separately from loading up facilities and covenant information.  Similary the write services can be asynchronously invoked through the usage of a Message Queue.  These worker tasks to write to db/target system can run independently and can scale independently as well.


4. Your solution most likely simulates the streaming process by directly calling a method in
   your code to process the loans inside of a for loop. What would a REST API look like for
   this same service? Stakeholders using the API will need, at a minimum, to be able to
   request a loan be assigned to a facility, and read the funding status of a loan, as well as
   query the capacities remaining in facilities.
    
-  Assign loan to a Facility:

    - to request a loan to be assigned
      HTTP POST: url/api/loans
      {"loanId": 1,
      "interestRate": "0.43",
      "amount": "121.31",
      "state": "TX",
      etc.
      }

- Post would respond with some status code and confirmation id/ unique id.

    - to see details on a newly requested loan
      HTTP GET: url/api/loans/[id]

- Get remaining facility capacity
      - HTTP GET: url/api/facilities/[id]
      {"facilityId": 1,
      "facilityName": "Facility Name1",
      "bankName": "BOA",
      "balance": "2334.23",
      etc.
      }

5. How might you improve your assignment algorithm if you were permitted to assign loans
   in batch rather than streaming? We are not looking for code here, but pseudo code or
   description of a revised algorithm appreciated.
    
- We can have bulk select (configurable amount) based on some conditions such as pick those up with the highest interest rates.  Would love to do this but ran out of time. Will do it post this submission for my own understanding.

6. Discuss your solution’s runtime complexity.

- Loading up facility and loan data are O(n) as I'm just iterating through the record set.
  For loading up Covenant data, it is still O(n) as I'm associating the Covenant data to map of facilities.  Assigning loans to facilities is not really efficient O(n^2) and can be tweaked.  Really not a fan of nested loops so would like to make those runtime complexity's better by perhaps using a map in more places.  

	
	
