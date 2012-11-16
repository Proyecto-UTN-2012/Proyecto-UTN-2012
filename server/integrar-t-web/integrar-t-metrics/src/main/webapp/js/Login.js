var userName = null;
var gender = null;
var auth = new OAuth({
	  context:window,
	  scope:"https://www.googleapis.com/auth/userinfo.profile",
	  clientId:"964953094604.apps.googleusercontent.com",
	  //redirectUri:"${local.metrics.url}",
	  redirectUri:"http://localhost:8081/integrar-t-metrics",
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
      url: "https://www.googleapis.com/oauth2/v1/userinfo?alt=json",
      dataType: "json",
      success: function(info){
    	userName = info.name;
    	gender = info.gender;
    	var welcome = (gender=="male")? "Bienvenido" : "Bienvenida";
    	welcome+= " " + userName;
    	$("#userTitle").text(welcome);
    	$("#loginPanel").attr("hidden", true);
    	$("#bodyPanel").removeAttr("hidden");
    	//$("#bodyPanel").css("visibility", "inherit");
      }
      
    });
};

function load(){
	if (auth.isTokenPresent()) {
	    // apparently we got back control after authentication
	    var accessToken = auth.extractTokenInfo();
	    onLogin(accessToken); // fire event
	}
};

function login(){
	auth.authorize();
}