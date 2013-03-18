template.conf syntax

The config file is based on JSON syntax.

file syntax:

	[ <config_element> , ... ]

config_element:

	{ "type" : <type> ,
	  "key" : <key> ,
	  "priority" : <priority>,
	  "desc" : <desc>,
	  <type_specific_def>, ...
	}
  
type: string

	"text" | "ipaddr" | "select"

key: string

	Variable name that will be recorded in the config file.
	
priority: numeric

	Determines element's position on the interface and in the config file.
	The smaller the priority is, the higher it is placed.

desc: string

	Description of the element. Will be displayed on the interface to help understanding the element.
	
type_specific_def:

	type="text":
		
		"allowWhitespace" : <allow>
		
		allow: boolean
			
			Indicates whether whitespaces (space, tab, newline, return, eof) are allowed in input.
			
	type="select":
		
		"options" : [ <option> , ... ]
		
		option:
			
			{ "value" : <value>, "desc" : <desc> }
			
		value: string
		
			Value to be recorded in the config file, an option of the select element.
			
		desc: string
		
			Description of the value. Will be displayed on the user interface to help selection.

			