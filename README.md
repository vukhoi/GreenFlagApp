# GreenFlagApp
Green Flag App

4 activities: MainActivity, CreateNewAccountActivity, AddAccountDetailActivity, ListAccountActivity <br />
 <br />
MainActivity: <br />
      &nbsp;&nbsp;&nbsp;landing page <br />
      &nbsp;&nbsp;&nbsp;create new account button: start CreateNewAccountActivity <br />
      &nbsp;&nbsp;&nbsp;hidden button top right corner: start ListAccountActivity <br />
 <br />              
Create New Account Activity: <br />
      &nbsp;&nbsp;&nbsp;email, password; both will be validated, if both valid, create SQLite database entry for account <br />
      &nbsp;&nbsp;&nbsp;errors pop up depending on type of error <br />
      &nbsp;&nbsp;&nbsp;how all items, except for tool bar and buttons, are populated on a vertical linear layout based on how which error detected <br />
      &nbsp;&nbsp;&nbsp;top left back button: go back to MainActivity <br />
      &nbsp;&nbsp;&nbsp;next button: go to AddAccountDetailActivity <br />
 <br />              
AddAccountDetailActivity: <br />
      &nbsp;&nbsp;&nbsp;add additional properties to the account <br />
      &nbsp;&nbsp;&nbsp;change photo button: start activity for picking an image from storage <br />
      &nbsp;&nbsp;&nbsp;choose birthdate: open date picker for picking birthdate <br />
      &nbsp;&nbsp;&nbsp;click on Postal address first time for typing, second time for selecting a country from a list <br />
      &nbsp;&nbsp;&nbsp;save button update the account based on which new property registered <br />
      &nbsp;&nbsp;&nbsp;username by default is similar to email <br />
<br />
ListAccountActivity: <br />
      &nbsp;&nbsp;&nbsp;Show current user account photo and username, if not started from MainActivity <br />
      &nbsp;&nbsp;&nbsp;List all created accounts and their properties, except for password <br />
      &nbsp;&nbsp;&nbsp;clear db button under and bottom right of tool bar for clearing database <br />
