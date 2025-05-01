package software.consultoria;

import java.time.LocalDate;

public class Validacoes {

    public static boolean validarFuncionario(String nome, String telefone, String cep, String Rua, String Cidade, String Bairro, String User, String senha, String ButtonMenu, LocalDate DataNas) {

        if (nome.isEmpty() || telefone.isEmpty() || cep.isEmpty() || Rua.isEmpty() || Cidade.isEmpty() || Bairro.isEmpty() || User.isEmpty() || senha.isEmpty() || ButtonMenu.isEmpty() || DataNas == null){
            Aviso.mostrarAviso("Preencha todos campos!","/Alert.fxml");
            return false;
        }
        if (ButtonMenu.equals("Cargo")){
            Aviso.mostrarAviso("Selecione uma opção\n em Cargo","/Alert.fxml");
            return false;
        }
        if (nome.length() > 30){
            Aviso.mostrarAviso("Nome deve conter\n 30 Caracteres","/Alert.fxml");
            return false;
        }else if (telefone.length() > 14){
            Aviso.mostrarAviso("Telefone deve conter ate\n 14 Caracteres","/Alert.fxml");
            return false;
        }else if (cep.length() > 8) {
            Aviso.mostrarAviso("Cep deve conter ate\n 8 Caracteres","/Alert.fxml");
            return false;
        }else if (Rua.length() > 35){
            Aviso.mostrarAviso("rua deve conter ate\n 35 Caracteres","/Alert.fxml");
            return false;
        }else if (Cidade.length() > 30) {
            Aviso.mostrarAviso("Cidade deve conter ate\n 30 Caracteres","/Alert.fxml");
            return false;
        }else if (Bairro.length() > 25) {
            Aviso.mostrarAviso("Bairro deve conter ate\n 25 Caracteres","/Alert.fxml");
            return false;
        }else if (User.length() > 8) {
            Aviso.mostrarAviso("Usuario deve conter ate\n 8 Caracteres","/Alert.fxml");
            return false;
        } else if (senha.length() > 16){
            Aviso.mostrarAviso("Senha deve conter ate\n 16 Caracteres","/Alert.fxml");
            return false;
        }else if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Nome deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else if (!telefone.matches("\\d+")) {
            Aviso.mostrarAviso("Telefone deve conter\n Somente Números!","/Alert.fxml");
            return false;
        }else if (!cep.matches("\\d+")) {
            Aviso.mostrarAviso("CEP deve conter\n Somente Números","/Alert.fxml");
            return false;
        }else if (!Rua.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Aviso.mostrarAviso("Rua deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else  if (!Cidade.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Cidade deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else if (!Bairro.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Bairro deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else if (User.length() < 4){
            Aviso.mostrarAviso("Usuario deve conter de 4\n ate 8 Caracteres","/Alert.fxml");
            return false;
        }else if (senha.length() < 6){
            Aviso.mostrarAviso("Senha deve conter de 6\n ate 16 Caracteres","/Alert.fxml");
            return false;
        }else if (!User.matches("[a-zA-Z0-9]+")) {
            Aviso.mostrarAviso("Usuario deve conter\n Letras e Números","/Alert.fxml");
            return false;
        }else if (!senha.matches("(?=.*[a-zA-Z])(?=.*\\d).+")){
            Aviso.mostrarAviso("Senha deve contrer\n Letras e Números", "/Alert.fxml");
            return false;
        }else if (User.contains(" ") || senha.contains(" ")) {
            Aviso.mostrarAviso("Usuario e senha não\n Podem conter espaços","/Alert.fxml");
            return false;
        }
        return true;
    }

    public static boolean validarFornecedor(String nome, String telefone, String email, String CNPJ, String cep, String Rua, String Cidade, String Bairro) {

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || CNPJ.isEmpty() || cep.isEmpty() || Rua.isEmpty() || Cidade.isEmpty() || Bairro.isEmpty()){
            Aviso.mostrarAviso("Preencha todos campos!","/Alert.fxml");
            return false;
        }
        if (nome.length() > 30){
            Aviso.mostrarAviso("Nome deve conter\n 30 Caracteres","/Alert.fxml");
            return false;
        }else if (telefone.length() > 14) {
            Aviso.mostrarAviso("Telefone deve conter ate\n 14 Caracteres","/Alert.fxml");
            return false;
        }else if (email.length() > 25){
            Aviso.mostrarAviso("Email deve conter ate\n 25 Caracteres","/Alert.fxml");
            return false;
        }else if (CNPJ.length() > 14){
            Aviso.mostrarAviso("CNPJ deve conter ate\n 14 Caracteres","/Alert.fxml");
            return false;
        }else if (cep.length() > 8){
            Aviso.mostrarAviso("CEP deve conter ate\n 8 Caracteres","/Alert.fxml");
            return false;
        }else if (Rua.length() > 35){
            Aviso.mostrarAviso("rua deve conter ate\n 35 Caracteres","/Alert.fxml");
            return false;
        }else if (Cidade.length() > 30) {
            Aviso.mostrarAviso("Cidade deve conter ate\n 30 Caracteres","/Alert.fxml");
            return false;
        }else if (Bairro.length() > 25) {
            Aviso.mostrarAviso("Bairro deve conter ate\n 25 Caracteres","/Alert.fxml");
            return false;
        }else if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Nome deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else if (!telefone.matches("\\d+")) {
            Aviso.mostrarAviso("Telefone deve conter\n Somente Numeros!","/Alert.fxml");
            return false;
        }else if (!CNPJ.matches("\\d+")) {
            Aviso.mostrarAviso("CNPJ deve conter\n Somente Numeros","/Alert.fxml");
            return false;
        }else if (!cep.matches("\\d+")) {
            Aviso.mostrarAviso("CEP deve conter\n Somente Numeros","/Alert.fxml");
            return false;
        }else if (!Rua.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Aviso.mostrarAviso("Rua deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else  if (!Cidade.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Cidade deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else if (!Bairro.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Bairro deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }

        return true;
    }

    public static boolean validarDespesas(String nome, String valor, String observacoes, String situacao, String pagamento,LocalDate data) {

        if (nome.isEmpty() || valor.isEmpty() || observacoes.isEmpty() || situacao.isEmpty() || pagamento.isEmpty() || data == null){
            Aviso.mostrarAviso("Preencha todos campos!","/Alert.fxml");
            return false;
        }
        if (nome.length() > 30){
            Aviso.mostrarAviso("Numero Maximo em\nDespesa é 30!","/Alert.fxml");
            return false;
        }else if (!valor.matches(".*\\d.*")){
            Aviso.mostrarAviso("Somente numeros\nEm Valor!","/Alert.fxml");
            return false;
        }else if (observacoes.length() >80){
            Aviso.mostrarAviso("Numero Maximo em\nObservações é 80!","/Alert.fxml");
            return false;
        }else if (situacao.isEmpty() || situacao.equals("Situação")){
            Aviso.mostrarAviso("Selecione uma opção\nEm situação","/Alert.fxml");
            return false;
        }else if (pagamento.isEmpty() || pagamento.equals("Forma de Pagamento")){
            Aviso.mostrarAviso("Selecione uma opção\nEm pagamento","/Alert.fxml");
            return false;
        }else if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Nome deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }

        return true;
    }


    public static boolean validarProduto(String Nome, String Quantidade, String ValorInvestido, String ValorDeVenda, String Categoria, String Detalhes, Integer idfornecedor) {

        if (Nome.isEmpty() || Quantidade.isEmpty() || ValorInvestido.isEmpty() || ValorDeVenda.isEmpty() || Categoria.isEmpty() || Detalhes.isEmpty()){
            Aviso.mostrarAviso("Preencha todos campos!","/Alert.fxml");
            return false;
        }
        if (idfornecedor == null){
            Aviso.mostrarAviso("Selecione um Fornecedor!","/Alert.fxml");
            return false;
        }
        if (Nome.length() > 30){
            Aviso.mostrarAviso("Numero Maximo em\nNome é 30!","/Alert.fxml");
            return false;
        }else if (!Quantidade.matches("\\d+(\\.\\d+)?")){
            Aviso.mostrarAviso("Somente numeros\nEm quantidade!","/Alert.fxml");
            return false;
        }else if (!ValorInvestido.matches("\\d+(\\.\\d+)?")){
            Aviso.mostrarAviso("Somente numeros em\nValor investido!","/Alert.fxml");
            return false;
        }else if (!ValorDeVenda.matches("\\d+(\\.\\d+)?")){
            Aviso.mostrarAviso("Somente numeros em\nValor de venda!","/Alert.fxml");
            return false;
        }else if (Categoria.isEmpty() || Categoria.equals("Categoria")){
            Aviso.mostrarAviso("Selecione uma opção\nEm categoria!","/Alert.fxml");
            return false;
        }else if (Detalhes.length() > 50){
            Aviso.mostrarAviso("Numero Maximo em\nObservações é 50!","/Alert.fxml");
            return false;
        }else if (!Nome.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Nome deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }else if (!Detalhes.matches("[a-zA-ZÀ-ÿ\\s]+")){
            Aviso.mostrarAviso("Detalhes deve conter\n Somente Letras","/Alert.fxml");
            return false;
        }

        return true;
    }
}
