# pappas-pojkar-tips-server

Status codes:

200 "Successful"

401 "Not logged in" // Redirect to login screen


412 "User not found"
413 "Invalid login"
408 "Login is suspended"
430 "Name is required"
431 "Phone is required"
432 "Password is required"
434 "Email is required"
443 "Nickname is taken"
444 "Email is taken"
462 "Unauthorized to delete another's account"



## API
// All use post
// Structure:

request = {
  head: {
    userId: Integer,
    token: String
  },
  data: {}
}

response = {
  head: {
    statusCode: Integer,
    success: Boolean,
    message: String
  },
  data: {}
}




# User


/getUsers
request = {}

response = {
  head: {
    statusCode: 200,
    success: true,
    message: "Success"
  },
  data: User[]
}



/getUserById
request = {
  data: Integer
}

response = {
  head: {
    statusCode: 200|412,
    success: Boolean,
    message: check docs
  },
  data: User
}



/login
request = {
  data: {
    email: "",
    password: ""
  }
}

response = {
  head: {
    statusCode: 200/413/408,
    success: Boolean,
    message: check docs
  },
  data: User
}




/addUser
request = {
  data: {
    name: "",
    nickname: "" //Optional
    email: "",
    password: "",
    phone: ""
  }
}

response = {
  head: {
    statusCode: 200/444/443,
    success: Boolean,
    message: check docs
  },
  data: User
}




/updateUser
request = {
  head: {
    userId:Integer,
    token: ""
  },
  data: {
    name: "",     //Optional
    nickname: "", //Optional
    email: "",    //Optional
    phone: ""     //Optional
  }
}

response = {
  head: {
    statusCode: 200/412/401/444/443,
    success: Boolean,
    message: check docs
  },
  data: User
}



/deleteUser
request = {
  head: {
    userId: Integer,
    token: ""
  },
  data: Integer
}

response = {
  head: {
    statusCode: 200/462/412/401,
    success: Boolean,
    message: check docs
  },
  data: Boolean
}