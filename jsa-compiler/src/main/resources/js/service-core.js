////////////////////////////////////////////////////////////////////
// @author Vesko Georgiev 
////////////////////////////////////////////////////////////////////

/*
HttpInterface = {
	send: function(
			method, // 'POST'
			url,
			headers, // { '': '' }
			data, // whatever
			callbackSuccess,
			callbackError)
	{

	},
	post: function(
			url, // 
			headers, // { '': '' }
			data, // whatever
			callbackSuccess,
			callbackError)
	{

	}
};
*/

function FormRequest(HttpInterface, url, postData) {
	this.getQueryString = function(obj) {
		result = "";

		for (param in obj) {
			result += (encodeURIComponent(param) + '=' + encodeURIComponent(obj[param]) + '&');
		}

		if (result) //it's not empty string when at least one key/value pair was added. In such case we need to remove the last '&' char
			result = result.substr(0, result.length - 1); //If length is zero or negative, substr returns an empty string [ref. http://msdn.microsoft.com/en-us/library/0esxc5wy(v=VS.85).aspx]

		return result;
	};
	this.fire = function(callback, callbackError) {
		var headers = {
			'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
		};
		HttpInterface.post(url, headers,
				this.getQueryString(postData),
				callback, callbackError);
	};
}

function JSONRequest(HttpInterface, url, postData) {
	this.fire = function(callback, callbackError) {
		var headers = {
			'Content-Type': "application/json"
		};
		HttpInterface.post(url, headers, postData, callback, callbackError);
	};
}

function EmptyRequest(HttpInterface, url) {
	this.fire = function(callback, callbackError) {
		HttpInterface.post(url, {}, null, callback, callbackError);
	};
}
