# GreenFlagApp
Green Flag App

4 activities: MainActivity, CreateNewAccountActivity, AddAccountDetailActivity, ListAccountActivity <br />
 <br />
MainActivity: <br />
      landing page <br />
      create new account button: start CreateNewAccountActivity <br />
      hidden button top right corner: start ListAccountActivity <br />
 <br />              
Create New Account Activity: <br />
      email, password; both will be validated, if both valid, create SQLite database entry for account <br />
      errors pop up depending on type of error <br />
      how all items, except for tool bar and buttons, are populated on a vertical linear layout based on how which error detected <br />
      top left back button: go back to MainActivity <br />
      next button: go to AddAccountDetailActivity <br />
 <br />              
AddAccountDetailActivity: <br />
      add additional properties to the account <br />
      change photo button: start activity for picking an image from storage <br />
      choose birthdate: open date picker for picking birthdate <br />
      click on Postal address first time for typing, second time for selecting a country from a list <br />
      save button update the account based on which new property registered <br />
      username by default is similar to email <br />
<br />
ListAccountActivity: <br />
      Show current user account photo and username, if not started from MainActivity <br />
      List all created accounts and their properties, except for password <br />
      clear db button under and bottom right of tool bar for clearing database <br />
