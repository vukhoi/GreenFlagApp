# GreenFlagApp
Green Flag App

4 activities: MainActivity, CreateNewAccountActivity, AddAccountDetailActivity, ListAccountActivity

MainActivity: 
      landing page
      create new account button: start CreateNewAccountActivity
      hidden button top right corner: start ListAccountActivity
              
Create New Account Activity:
      email, password; both will be validated, if both valid, create SQLite database entry for account
      errors pop up depending on type of error
      how all items, except for tool bar and buttons, are populated on a vertical linear layout based on how which error detected
      top left back button: go back to MainActivity
      next button: go to AddAccountDetailActivity
              
AddAccountDetailActivity:
      add additional properties to the account
      change photo button: start activity for picking an image from storage
      choose birthdate: open date picker for picking birthdate
      click on Postal address first time for typing, second time for selecting a country from a list
      save button update the account based on which new property registered
      username by default is similar to email
      
ListAccountActivity:
      Show current user account photo and username, if not started from MainActivity
      List all created accounts and their properties, except for password
      clear db button under and bottom right of tool bar for clearing database
