### Output parsing

* Challenge
  * LLMs output java.util.String
  * Even when we ask for JSON, we get "JSON String"
    * So we need to serialise that to our Object of choice
  * As a Java developer we want to work with Objects, Chat GPT wants to chat, so do other LLMs

* Solution
  * Spring AI's output parser
    * The OutputParser interface allows you to obtain structured output, for example mapping the LLM String output to a 
      Java class or an array of values. It's similar to Spring JDBC concept of a RowMapper or ResultSetExtractor.
  * Has three implementations
    * BeanOutputParser 
      * It specifies the JSON schema for the Java class in whose format we want the response. The LLM JSON output is
        then converted to the POJO of our choice.
    * MapOutputParser
      * Same as the BeanOutputParser, however, JSON response is converted into Map<String, Object>
    * ListOutputParser
      * Output here is a comma delimited list