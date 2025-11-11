package sw.momolab.server.converter;

import sw.momolab.server.web.dto.AuthDTO.AuthResponseDTO;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

public class AuthConverter {
    public static AuthResponseDTO.LoginResponseDTO toLoginResponseDTO(TokenResponseDTO.TokenDTO tokenDTO) {
        return AuthResponseDTO.LoginResponseDTO.builder()
                .tokens(tokenDTO)
                .build();
    }
}