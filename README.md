# Program # 5
Name:  Todd Tingey
Cosc 4730

Description:  

	Run the app and use the previous program to add, edit, and delete transaction values and categories.

	Phone: Samsung Galaxy Note 5, 5.7"(Marshmallow 6.0.1)

Anything that doesn't work:
	
	Everything is working apart from a random cursor error with my spinner which Ward said is acceptable 


# Your grade:   50/50



# Problem 1:

You have this code which can cause an exception:

    selection = selection + "_id = " + uri.getLastPathSegment();

It should be:

    selection = selection + " AND _id = " + uri.getLastPathSegment();

The reason is that a selection clause like "AGE=19" will be translated into something like "AGE=19_id=34" instead of "AGE=19 AND _id=34"



# Minor problem:

* *Coding style (-2):* Classes should start with uppercase and be in CamelCase as per Java coding conventions. So "prog5ContentProvider" should be "Prog5ContentProvider".
