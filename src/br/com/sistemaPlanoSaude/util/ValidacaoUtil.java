package br.com.sistemaPlanoSaude.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public final class ValidacaoUtil {

    private ValidacaoUtil() {
        // Evita que a classe seja instanciada
    }

    // ===============================
    // Validação de Nome
    // ===============================
    public static boolean validarNome(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        nome = nome.trim();

        if (nome.length() < 2 || nome.length() > 70) {
            return false;
        }

        return nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
    }

    // ===============================
    // Validação de Email
    // ===============================
    public static boolean validarEmail(String email) {

        if (email == null) return false;

        email = email.trim();

        if (email.isEmpty()) return true; // opcional se não for obrigatório

        String regex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

        return email.matches(regex);
    }

    // ===============================
    // Validação de CPF
    // ===============================
    public static boolean validarCPF(String cpf) {

        if (cpf == null) return false;

        cpf = cpf.replace(".", "").replace("-", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;

            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }

            int resto = (soma * 10) % 11;
            if (resto == 10) resto = 0;

            if (resto != (cpf.charAt(9) - '0')) return false;

            soma = 0;

            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }

            resto = (soma * 10) % 11;
            if (resto == 10) resto = 0;

            return resto == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }   

    // ===============================
    // Validação de Data de Nascimento
    // ===============================
    public static boolean validarDataNascimento(String data) {

        if (data == null || data.trim().isEmpty()) return false;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate d = LocalDate.parse(data, fmt);
            LocalDate hoje = LocalDate.now();

            if (d.isAfter(hoje)) return false;

            if (d.isBefore(LocalDate.of(1900, 1, 1))) return false;

            return true;

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // ===============================
    // Validação de Idade
    // ===============================
    public static boolean validarIdade(Integer idade) {

        if (idade == null) return false;

        return idade > 0 && idade <= 150;
    }

    // ===============================
    // Validação e Formatação de Telefone
    // ===============================
    public static String validarEFormatarTelefone(String telefone) {

        if (telefone == null || telefone.trim().isEmpty()) {
            return null;
        }

        telefone = telefone.replaceAll("[^0-9]", "");

        if (telefone.length() == 11) {
            return String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 7),
                    telefone.substring(7, 11));
        } 
        else if (telefone.length() == 10) {
            return String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 6),
                    telefone.substring(6, 10));
        } 
        else {
            return null;
        }
    }

    // ===============================
    // Verifica se é celular
    // ===============================
    public static boolean isCelular(String telefone) {

        if (telefone == null) return false;

        telefone = telefone.replaceAll("[^0-9]", "");

        return telefone.length() == 11 && telefone.charAt(2) == '9';
    }

    // ===============================
    // Remove acentos
    // ===============================
    public static String removerAcentos(String texto) {

        if (texto == null) return null;

        return java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
// ===============================
    // Validação de CRM 
    // ===============================
public static boolean validarCRM(String crm) {

    if (crm == null) return false;

    return crm.matches("^[0-9]{4,6}-[A-Z]{2}$");

}






}