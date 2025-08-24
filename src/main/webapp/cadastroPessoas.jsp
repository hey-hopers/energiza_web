<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/CadastroUsuario/cadastroPessoas.css">
    <script>
        window.onload = function() {
            // Ao carregar a p gina, verificar se a URL tem algum par metro para mostrar um campo espec fico
            document.querySelectorAll(".image").forEach(img => img.classList.remove('active'));

            document.getElementById("image1").classList.add('active');
        };


        function voltarParaInfo() {
            mostrarFormulario('form-pessoa-info');
        }

        function voltarParaCadastro() {
            mostrarFormulario('form-pessoa-tipo');
        }

        function avancarParaEndereco(event) {
            event.preventDefault();
            mostrarFormulario('form-pessoa-endereco');
        }

        function avancarParaInfo(event, button) {
            event.preventDefault();
            document.getElementById('tipoPessoa').value = button.value;
            
            document.getElementById('campo-cpf').style.display = (document.getElementById('tipoPessoa').value === '1') ? 'block' : 'none';
            document.getElementById('campo-cnpj').style.display = (document.getElementById('tipoPessoa').value === '2') ? 'block' : 'none';
            
            mostrarFormulario('form-pessoa-info');
        }

        function mostrarFormulario(formId) {
            // Resetar destaques
            document.querySelectorAll(".image").forEach(img => img.classList.remove('active'));

            // Ativar o item selecionado
            if (formId === 'form-pessoa-tipo') {
                document.getElementById("image1").classList.add('active');
                
            } else if (formId === 'form-pessoa-info') {
                document.getElementById("image2").classList.add('active');

            } else if (formId === 'form-pessoa-endereco') {
                document.getElementById("image3").classList.add('active');

            }
               
            document.querySelectorAll("form").forEach(form => form.style.display = "none");
            document.getElementById(formId).style.display = "block";
        }  

        function enviarFormulario() {
            // Capturar os valores dos formulários
            document.getElementById('inputTipoPessoa').value = document.getElementById('tipoPessoa').value;
            document.getElementById('inputApelido').value = document.querySelector('[name="apelido"]').value;
            document.getElementById('inputNome').value = document.querySelector('[name="nome"]').value;
            document.getElementById('inputCPF').value = document.querySelector('[name="cpf"]').value;
            document.getElementById('inputCNPJ').value = document.querySelector('[name="cnpj"]').value;
            document.getElementById('inputCEP').value = document.querySelector('[name="cep"]').value;
            document.getElementById('inputEndereco').value = document.querySelector('[name="endereco"]').value;
            document.getElementById('inputNumero').value = document.querySelector('[name="numero"]').value;
            document.getElementById('inputComplemento').value = document.querySelector('[name="complemento"]').value;
            document.getElementById('inputBairro').value = document.querySelector('[name="bairro"]').value;
            document.getElementById('inputCidade').value = document.querySelector('[name="cidade"]').value;
            document.getElementById('inputEstado').value = document.querySelector('[name="estado"]').value;
            document.getElementById('inputPais').value = document.querySelector('[name="pais"]').value;

            // Capturar redes sociais
            const plataformas = document.querySelectorAll('[name="plataforma[]"]');
            const telefones = document.querySelectorAll('[name="telefone[]"]');
            const urls = document.querySelectorAll('[name="url[]"]');

            // Limpar campos ocultos de redes sociais antes de adicionar novos
            document.getElementById('redesOcultas').innerHTML = "";

            plataformas.forEach((plataforma, index) => {
                const inputPlataforma = document.createElement("input");
                inputPlataforma.type = "hidden";
                inputPlataforma.name = "plataforma[]";
                inputPlataforma.value = plataforma.value;

                const inputTelefone = document.createElement("input");
                inputTelefone.type = "hidden";
                inputTelefone.name = "telefone[]";
                inputTelefone.value = telefones[index].value;

                const inputUrl = document.createElement("input");
                inputUrl.type = "hidden";
                inputUrl.name = "url[]";
                inputUrl.value = urls[index].value;

                document.getElementById('redesOcultas').appendChild(inputPlataforma);
                document.getElementById('redesOcultas').appendChild(inputTelefone);
                document.getElementById('redesOcultas').appendChild(inputUrl);
            });

            // Enviar o formulário oculto para o servlet
            document.getElementById('form-pessoa').submit();
        }      
        

        function adicionarRedeSocial() {
            const container = document.getElementById("redes-sociais-lista");

            container.style.display = "block";

            const novaRedeSocial = document.createElement("div");
            novaRedeSocial.classList.add("rede-social");
            novaRedeSocial.innerHTML = `
                <input type="text" name="plataforma[]" placeholder="Plataforma">
                <input type="text" name="telefone[]" placeholder="Telefone">
                <input type="text" name="url[]" placeholder="URL">
                <button type="button" class="botao-remover" onclick="removerRedeSocial(this)">X</button>
            `;

            container.appendChild(novaRedeSocial);
        }

        function removerRedeSocial(botao) {
            botao.parentElement.remove();
        }

    </script>
</head>

<body>
    <div class="container">
        <div class="image-container">
            <div class="image-wrapper">
                <div class="image" id="image1" onclick="mostrarFormulario('form-pessoa-tipo')">
                    <img src="imagens/user.png" alt="tipo">
                </div>
                <div class="triangle" id="trianglecadastro"></div>
            </div>
            <div class="image-wrapper">
                <div class="image" id="image2" onclick="mostrarFormulario('form-pessoa-info')">
                    <img src="imagens/document.png" alt="info">
                </div>
                <div class="triangle" id="triangleinfo"></div>
            </div>
            <div class="image-wrapper">
                <div class="image" id="image3" onclick="mostrarFormulario('form-pessoa-endereco')">
                    <img src="imagens/home.png" alt="endereco">
                </div>
                <div class="triangle" id="triangleendereco"></div>
            </div>
        </div>

        <div class="form-container">
            <!-- Formul rio 1 - Tipo de Pessoa -->
            <form id="form-pessoa-tipo">
                <h2>Voc uma pessoa física ou uma empresa?</h2>
                <p>Estas informações sero utilizadas para melhorar a sua experiência de uso.</p>
                <input type="hidden" id="tipoPessoa" name="tipoPessoa">
                 <div class="botao-container">
			        <button type="button" name="tipoPessoa" value="1" onclick="avancarParaInfo(event, this)" class="botao-imagem">
			            <div>
			                <img src="imagens/user.png" alt="Pessoa Física" style="width: 80px; height: 80px;">
			            </div>
			            <span>Pessoa Física</span>
			        </button>
			        <button type="button" name="tipoPessoa" value="2" onclick="avancarParaInfo(event, this)" class="botao-imagem">
			            <div>
			                <img src="imagens/company.png" alt="Empresa" style="width: 80px; height: 80px;">
			            </div>
			            <span>Empresa</span>
			        </button>
			    </div>
            </form>

            <!-- Formul rio 2 - Informa  es Pessoais -->
            <form id="form-pessoa-info" style="display: none;">
                <h2>Precisamos de algumas informações sobre voc .</h2>
                <input type="text" name="nome" placeholder="Nome">
                 <div id="campo-cpf" style="display: none;">
                 	<input type="text" name="apelido" placeholder="Apelido">
			        <input type="text" name="cpf" placeholder="CPF">
			    </div>
			
			    <div id="campo-cnpj" style="display: none;">
			    	<input type="text" name="nome-fantasia" placeholder="Nome Fantasia">
			        <input type="text" name="cnpj" placeholder="CNPJ">
			    </div>
                <div id="redes-sociais-container"> 
                    <h2>Redes Sociais</h2>
                    <button type="button" onclick="adicionarRedeSocial()">+</button>                                       
                </div>
                <div id="redes-sociais-lista" style="display: none;"></div>  
			    		    
			    
                <div class="botao-container">
                    <button type="button" onclick="voltarParaCadastro()">Voltar</button>
                    <button type="button" onclick="avancarParaEndereco(event)">Avançar</button>                
                </div>
            </form>

            <!-- Formulário 3 - Endereço -->
            <form id="form-pessoa-endereco" style="display: none;">
                <h2>Informe o endereço</h2>
                
                <div class="form-group">
                    <div class="input-container">
                        <input type="text" name="cep" placeholder="CEP">
                        <input type="text" name="endereco" placeholder="Endereço">
                    </div>
                    
                    <div class="input-container">
                        <input type="text" name="numero" placeholder="Número">
                        <input type="text" name="complemento" placeholder="Complemento">
                    </div>
                    
                    <div class="input-container">
                        <input type="text" name="bairro" placeholder="Bairro">
                        <input type="text" name="cidade" placeholder="Cidade">
                    </div>
                    
                    <div class="input-container">
                        <input type="text" name="estado" placeholder="Estado">
                        <input type="text" name="pais" placeholder="País">
                    </div>
                </div>

                <div class="botao-container">
                    <button type="button" onclick="voltarParaInfo()">Voltar</button>
                    <button type="button" onclick="enviarFormulario()">Finalizar</button>
                </div>
            </form>

            <!-- Formul rio oculto para envio final -->
            <form id="form-pessoa" action="cadastroPessoas" method="post" style="display: none;">
                <input type="hidden" id="inputTipoPessoa" name="tipoPessoa">
                <input type="hidden" id="inputApelido" name="apelido">
                <input type="hidden" id="inputNome" name="nome">
                <input type="hidden" id="inputCPF" name="cpf">
                <input type="hidden" id="inputCNPJ" name="cnpj">
                <input type="hidden" id="inputCEP" name="cep">
                <input type="hidden" id="inputEndereco" name="endereco">
                <input type="hidden" id="inputNumero" name="numero">
                <input type="hidden" id="inputComplemento" name="complemento">
                <input type="hidden" id="inputBairro" name="bairro">
                <input type="hidden" id="inputCidade" name="cidade">
                <input type="hidden" id="inputEstado" name="estado">
                <input type="hidden" id="inputPais" name="pais">
            
                <!-- Contêiner para redes sociais -->
                <div id="redesOcultas"></div>
            </form>
        </div>
    </div>
</body>
</html>