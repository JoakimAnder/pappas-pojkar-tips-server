(window["webpackJsonppappas-pojkar-tips-client"]=window["webpackJsonppappas-pojkar-tips-client"]||[]).push([[0],{14:function(e,t,a){e.exports=a(38)},19:function(e,t,a){},20:function(e,t,a){},38:function(e,t,a){"use strict";a.r(t);var n=a(0),r=a.n(n),l=a(13),o=a.n(l),c=(a(19),a(2)),u=(a(20),a(3));function i(e){return r.a.createElement("div",null,r.a.createElement("nav",null,r.a.createElement("button",{onClick:function(){return e.setPage("profile")}},"Go to profile")),r.a.createElement("main",null,r.a.createElement("form",{name:"login",onSubmit:function(t){t.preventDefault();var a=t.target.email.value,n=t.target.password.value;u.post("http://diceit.itancan.com:8604/login",{data:{email:a,password:n}}).then(function(t){if(console.log(t),t.data.head.successful){var a=t.data.data;e.setUser(a),e.setToken(a.token),e.setPage("profile")}else e.setError(t.data.head.message)}).catch(console.log)}},r.a.createElement("input",{required:!0,placeholder:"Email",name:"email",defaultValue:e.user?e.user.email:""}),r.a.createElement("input",{required:!0,placeholder:"Password",name:"password",defaultValue:""}),r.a.createElement("button",{type:"submit"},"Login")),r.a.createElement("button",{onClick:function(){return e.setPage("registration")}},"Sign Up!")),r.a.createElement("footer",null,"footer"))}var s=a(3);function m(e){var t=Object(n.useState)("I don't know!"),a=Object(c.a)(t,2),l=a[0],o=a[1],u=e.user;if(null===u)return e.setError("You are not logged in!"),e.setPage("login"),r.a.createElement("div",null);return r.a.createElement("div",null,r.a.createElement("nav",null,r.a.createElement("button",{onClick:function(){return e.setPage("login")}},"Go to login")),r.a.createElement("main",null,r.a.createElement("span",null,r.a.createElement("h3",null,"Name: "),r.a.createElement("p",null,u.name)),r.a.createElement("span",null,r.a.createElement("h3",null,"Nickname: "),r.a.createElement("p",null,u.nickname)),r.a.createElement("span",null,r.a.createElement("h3",null,"Phone: "),r.a.createElement("p",null,u.phone)),r.a.createElement("span",null,r.a.createElement("h3",null,"Paystatus: "),r.a.createElement("p",null,u.paystatus)),r.a.createElement("button",{onClick:function(){s.post("http://diceit.itancan.com:8604/IsLoggedIn?email=".concat(u.email,"&token=").concat(u.token)).then(function(e){return 200==e.status?e.data:"I don't know! (Something went wrong)"}).then(o)}},"Check if logged in"),r.a.createElement("h2",null,l)),r.a.createElement("footer",null,"footer"))}var p=a(3);function d(e){return r.a.createElement("div",null,r.a.createElement("form",{onSubmit:function(t){t.preventDefault();var a=t.target.name.value,n=t.target.nickname.value,r=t.target.phone.value,l=t.target.email.value,o=t.target.password.value;p.post("http://diceit.itancan.com:8604/addUser",{headers:{},data:{name:a,nickname:n,phone:r,email:l,password:o}}).then(function(t){t.data.head.successful?(e.setUser(t.data.data),e.setPage("login")):e.setError(t.data.head.message)})}},r.a.createElement("input",{required:!0,name:"email",placeholder:"Email"}),r.a.createElement("input",{required:!0,name:"password",placeholder:"Password"}),r.a.createElement("input",{required:!0,name:"name",placeholder:"Name"}),r.a.createElement("input",{name:"nickname",placeholder:"Nickname (optional)"}),r.a.createElement("input",{required:!0,name:"phone",placeholder:"Phone"}),r.a.createElement("button",{type:"Submit"},"Submit")))}var E=function(){var e=Object(n.useState)(""),t=Object(c.a)(e,2),a=t[0],l=t[1],o=Object(n.useState)(""),u=Object(c.a)(o,2),s=(u[0],u[1]),p=Object(n.useState)(null),E=Object(c.a)(p,2),g=E[0],f=E[1],h=Object(n.useState)(""),v=Object(c.a)(h,2),b=v[0],k=v[1];return r.a.createElement("div",{className:"App"},r.a.createElement("h4",null,b),function(e){switch(e){case"profile":return r.a.createElement(m,{setPage:l,user:g,setError:k});case"registration":return r.a.createElement(d,{setError:k,setUser:f,setPage:l});default:return r.a.createElement(i,{setError:k,setToken:s,setUser:f,setPage:l,user:g})}}(a))};o.a.render(r.a.createElement(E,null),document.getElementById("root"))}},[[14,1,2]]]);
//# sourceMappingURL=main.22d1c17a.chunk.js.map