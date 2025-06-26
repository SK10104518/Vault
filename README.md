# Vault
A personalized budget tracking app Part 1

Research

Introduction

A need in today's busy life is a personal budgeting app that tracks your money spending habits, the good & the bad Ones. It helps in making us aware of where we can improve our spending habits and how we can invest in tomorrow. With how demanding our economy is this app will help alleviate the stress associated with managing finances and encourage users to adopt better spending habits.Therfore I'll be conducting research on 3 already existing personal budgeting apps.  

RESEARCH ON 3 EXISTING APPS

1.Mint
-Review: After conducting research I discovered Mint is A exceedingly popular budgeting app which was created in 2006 by Former internet entrepreneur and DEVELOPER Aaron Patzer.Mint aggrgates user financial data from various accounts.
-Strenghts: During the researchI noticed the strengths of this app are :
Its user-friendly interface
Comprehensive expense tracking
Cost effecetive & 	Robust reporting  features
-Weakness: Contrary to its strenghts the apps weknesses are:
Limited customaization options for budgets
Maximalist interface
-Innovative Features: The app does though have its own unique innovative features which are:
Automatic categorization of user expense using AI.

2. YNAB(You Need A Budget)
-Review: After extensive research I discovered YNAB helps users by focusing on proactive budgeting and encourages
-Strenghts: During the researchI noticed the strengths of this app are :
Its has strong educational resources to assist the user understand and learn.
It has a flexible community support in in need of help with relatable problems and to come up with solutions.
Detailed & powerful reporting as well as expense tracking visualization.
-Weakness: Contrary to its strenghts the weknesses I discovered are:
That it has Subsccription-based model which may deter some users due to wanting the best of thr app but unable to afford it.
Manual data entry
-Innovative Features: The app does though have its own unique innovative features which are:
Real-Time tracking of spending against budgeted amounts.

3.PocketGuard
-Review: A discovery I made with PocketGuard is that it makes budgeting easier for the user by showing how much disposable income users have after accounting for bills and goals.That users may use anyway they wish .
-Strenghts: During the researchI noticed the strengths of this app are :
Its has an easy-to-understand visualizations of spending & High reputation. 
-Weakness: Contrary to its strenghts the weknesses I discovered are:
That it has Limited advanced features compared to competitors account connectivity issues
Users have stated to loss of past transaction when using the app
-Innovative Features: The app does though have its own unique innovative features which are:
"In My Pocket" feature that helps users understand their spending limits. 

Best Features List 

Expense categorization 

Real-time tracking 

Visual spending reports 

Gamification elements such as rewards for meeting budget goals 

Conclusion 

Thus, after researching & examining these 3 apps I have gained insight on how to maximize the potential of my personal budgeting tracking app. The app needs to be user-friendly & not complicating, encouraging users to be aware of their spending habits and reward them for goals such as improving on their spending habits & making wise decisons with their income. 



Best Features List 

Expense categorization 

Real-time tracking 

Visual spending reports 

Gamification elements such as rewards for meeting budget goals 

Conclusion 

Thus, after researching & examining these 3 apps I have gained insight on how to maximize the potential of my personal budgeting tracking app. The app needs to be user-friendly & not complicating, encouraging users to be aware of their spending habits and reward them for goals such as improving on their spending habits & making wise decisons with their income. 


Planning and Design  

Introduction 

In this section after my extensive research on the 3 previous personal budget tracking apps.I have gone into planning out on how and when i plan on designing,implementing and deploying my own application.

App Overview 

Name: Vault Vibe (VV) 

Icon Design: 

  

Innovative Features Description:  

- Apon research Badges for savings milestones incite users to be more engaged with apps and helping them stay focused on their goals . 

Detailed Requirements List 

Provide a comprehensive list of requirements based on the minimum features specified: 

User registration and login functionality. 

Expense entry with details (amount, date, description, category). 

Photo attachment option for receipts. 

Monthly budget goals and category limits. 

Viewing expenses over selectable periods. 

Graphical representation of spending trends. 

Progress dashboard highlighting budget adherence. 

Gamification elements like rewards or badges. 

User Interface Design 

Mockups and their purpose for each screen of the app: 


Registration 

-Is designed to go together with the login page to acquire the user's information & save the credentials within the database for future user authentication attempts 

Login 

-Like the Registration page they work together to securely authenticate users allowing them to access their personal financial data through inputting their credentials. 


Home Screen 

-Is the first page after logging in to the app it is a dashboard that provides an overview of the user's financial situation. It will display key metrics such as your account balances, recent transactions, budget status. 

 
Expense Entry Screen 

-Contains the budgeting tools that help users enter, set & manage their budgets across various categories, descriptions and ability to capture their receipts. 

-Expense List Screen with receipt access & Spending Trends Graph Screen 

-Allows users to access & view their past recorded transactions as well as their spending graph 

Progress Dashboard 

-Helps the user set & view the progress of their goals they set for that period and access to their rewards after completing their goals.

Project Plan 

Conclusion 
In conclusion the process may not be perfect but application will meet the required features with its own unique innovative features

References 

 
Custom Feature 1: Expense Filtering

The app allows users to filter their expense list and statistics chart by three predefined time periods:
This Week:Shows all transactions from the current week.
This Month: Shows all transactions from the current calendar month (default view).
All Time:*Shows a complete history of all transactions.
This is implemented using a `ChipGroup` in the UI and a reactive data flow in the `MainViewModel` using `LiveData` transformations.

Feature 2 :Badge Awarding Feature
The badge awarding feature is part of the StatisticsFragment, which displays spending statistics and user goal progress. It rewards users by playing a success sound when their total spending for a selected time period (week, month, or all-time) falls within their predefined monthly goal range (monthlyGoalMin and monthlyGoalMax). This feature enhances user engagement by providing audible feedback for achieving financial goals.
Feature Location:

File: StatisticsFragment.kt (package: com.example.vault.ui.main)

Key Function: playSuccessSound()

Trigger Location: Inside the onViewCreated method, within the authViewModel.currentUser and viewModel.categorySpending observers.
