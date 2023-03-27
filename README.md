# UseData
Personal project involving data I/O and database structures.

Two things I really enjoy are maintaining records of the things I do and managing databases. 
So, this January I wrote a short program which would help calculate statistics of my own records.

I use a text file, which I manually write to, to record art projects I have created and the dates 
I edit them. I mostly draw traditional art, so in my text file I assign projects a numeric value in 
chronological order. Underneath each entry is a list of dates for when I've edited the artwork. A 
few entries would look like this:

111
     01.23.19
112
     04.13.22
     01.12.21

I wrote a tool to read from this file, automatically creating UseData objects for each block of 
an integer and the corresponding dates. I also used this program to create a few functions which 
could answer some questions I had about my records, such as the project with the earliest recorded 
edit data and the project which has been edited the most times. I had a lot of fun creating something 
which I can actually use on a weekly basis, and plan to add more features to the program other than 
the two listed.
