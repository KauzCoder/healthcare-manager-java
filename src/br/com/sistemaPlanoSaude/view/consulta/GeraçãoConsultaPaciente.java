//  String numeroCarteirinha;
//         while (true) {
//             System.out.print("Número da carteirinha: ");
//             numeroCarteirinha = scanner.nextLine().trim();

//             if (numeroCarteirinha.isEmpty()) {
//                 System.out.println("Número de carteirinha não pode ser vazio.");
//                 continue;
//             }

//             boolean jaRegistrada = ValidacaoUtil.validarCarteirinhaSaude(
//                 numeroCarteirinha,
//                 PlanoSaude.listarCarteirinhasBasico(),
//                 PlanoSaude.listarCarteirinhasPremium()
//             );

//             if (jaRegistrada) {
//                 System.out.println("Esta carteirinha já está associada a um plano. Informe outro código.");
//                 continue;
//             }

//             if (planoSelecionado.registrarCarteirinhaPaciente(numeroCarteirinha)) {
//                 break;
//             }

//             System.out.println("Não foi possível registrar a carteirinha. Tente novamente.");
//         }
