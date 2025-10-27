
package uni.aed.tda.AnalizadorSintactico;
import uni.aed.tda.linkedlistTDA.LinkedListTDA;
import uni.aed.tda.listTDA.ListTDA; 
import java.util.regex.*;
public class AnalizadorLexico {
 public ListTDA<Token> tokenizar(String consulta) {
        ListTDA<Token> tokens = new LinkedListTDA<>();

        // Expresiones identificar 
        Pattern patron = Pattern.compile(
            "(SELECT|FROM|WHERE|AND|OR)|" +   // palabras clave
            "([a-zA-Z_][a-zA-Z0-9_]*)|" +     // identificadores
            "(<=|>=|<>|=|<|>)|" +             // operadores
            "('[^']*'|[0-9]+)|" +             // valores
            "([,;])"                          // delimitadores
        );

        Matcher m = patron.matcher(consulta);

        while (m.find()) {
            String token = m.group();

            if (token.matches("SELECT|FROM|WHERE|AND|OR"))
                tokens.add(new Token(TokenType.KEYWORD, token.toUpperCase()));
            else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*"))
                tokens.add(new Token(TokenType.IDENTIFIER, token));
            else if (token.matches("<=|>=|<>|=|<|>"))
                tokens.add(new Token(TokenType.OPERATOR, token));
            else if (token.matches("'[^']*'|[0-9]+"))
                tokens.add(new Token(TokenType.VALUE, token.replace("'", "")));
            else if (token.matches("[,;]"))
                tokens.add(new Token(TokenType.DELIMITER, token));
        }

        return tokens;
    }

}
    
