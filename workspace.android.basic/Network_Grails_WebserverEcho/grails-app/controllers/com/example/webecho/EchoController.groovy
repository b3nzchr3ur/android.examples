package com.example.webecho

import grails.converters.deep.JSON

class EchoController {
 	static final Integer incorrectNumber = -1
	 
	 def index = {
		String message = "Nothing read" 
		 
		def inputStream = request.getInputStream()
		if(inputStream) {
			message = inputStream.getText()
		}
		
		render "Echo: " + message
	 }
}
