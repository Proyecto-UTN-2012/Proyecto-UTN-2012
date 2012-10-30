var userName = null;
var auth = new OAuth({
	  context:window,
	  scope:"https://www.googleapis.com/auth/userinfo.email",
	  clientId:"1068131665254.apps.googleusercontent.com",
	  redirectUri:"http://localhost:8080/integrar-t-settings/",
	  authorizationEndpoint:"https://accounts.google.com/o/oauth2/auth"
	});

//Overriding a jQuery default beforeSend, to append an access token header
var oauthAjax = function(options) {
	var originalBeforeSend = null;
  if (options.beforeSend) {
    originalBeforeSend = options.beforeSend;
  }
  options.beforeSend = function(xhr, settings) {
    originalBeforeSend && originalBeforeSend(xhr, settings);
    xhr.setRequestHeader('Authorization', "Bearer " + options.accessToken);
  };
  return $.ajax(options);
};

//Event handler after having retrieved an access token
var onLogin = function (token) {
	//auth.authorize();
  console.log("Obtained an OAuth Access token: " + token);
  oauthAjax({
      accessToken: token,
      url: "https://www.googleapis.com/userinfo/email?alt=json",
      dataType: "json",
      success: function(info){
    	user = info.data.email; 
    	$("#loginPanel").attr("hidden", true);
    	$("#accordion").css("visibility", "inherit");
      }
      
    });
};

function login(){
	$("#accordion").css("visibility", "hidden");
	auth.authorize();
	  // Attach click handler
//	  $("a#loginBtn").click(function () {
//	    auth.authorize(); // Start the OAuth-flow. This will lose control
//	    return false; // prevent default.
//	  });
}