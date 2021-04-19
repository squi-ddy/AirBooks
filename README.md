# AirBooks

The so called "AirBooks" service for book rental (which totally exists).\
Online book rental has never been easier!

## Features
- Browse our ***extensive*** booklist!
- Rent any of our ***100*** books!
- Rent books online, get a ***physical*** copy!
- $25.00 wallet that ***cannot be topped up***!
- New account creation ***nonexistent***!
- ***Island-wide*** service<sup>1</sup>!
- No collection points inside ***NUS High School***!
- View ***all*** the accounts, ***in plain text***<sup>2</sup>!
- Completely ***open source***<sup>3</sup>!

<sup>1</sup>Only usable by NUS High Students\
<sup>2</sup>```src/airbooks/csv/Secure.csv```\
<sup>3</sup>Not by choice (also this repo is private)

## Reason this exists
- CS3231 Object-Oriented Programming I: Programming Assignment

### Development stage
All features complete. Currently, refinement of the UI and bug-fixing is in progress.

### Todo
- Change jar artifact output location
- Rename jarMain to JarMain
- Reset all controllers upon window exit (causes a buh with SCSController)
- Split student controller reload function into 3 parts: info, rentalCart, search (rentalCart need not be reloaded upon trash)
- Use Map optimisation in Main.java too