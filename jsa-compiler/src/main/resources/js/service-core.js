/*******************************************************************************
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@icloud.com">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *******************************************************************************/
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
