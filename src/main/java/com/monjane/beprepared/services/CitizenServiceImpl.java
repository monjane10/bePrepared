package com.monjane.beprepared.services;

import com.monjane.beprepared.exception.BadRequestException;
import com.monjane.beprepared.exception.EntityNotFoundException;
import com.monjane.beprepared.model.Citizen;
import com.monjane.beprepared.model.City;
import com.monjane.beprepared.model.enums.Role;
import com.monjane.beprepared.repository.CitizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;



@Service
@RequiredArgsConstructor


public class CitizenServiceImpl implements CitizenService {

    private final LocationService locationService;
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String createCitizen(Citizen citizen, Long cityId) {

        if (citizenRepository.existsByPhone(citizen.getPhone())) {
            throw  new BadRequestException("JA existe cidadao com esse numero!");
        }

        City city = locationService.getCityById(cityId);
        citizen.setCity(city);
        citizen.setVerified(false);
        citizen.setRole(Role.USER);
        citizen.setOtp(generateOtp(6));
        var SavedCitizen = citizenRepository.save(citizen);
        return "Cidadao adicionado com sucesso! O seu codigo de verificação é: " + SavedCitizen.getOtp();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public List<Citizen> getAllCitizensCityId(Long cityId) {
        return citizenRepository.findAllByCityId(cityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Citizen> getAllCitizensByProvinceId(Long provinceId) {
        return citizenRepository.findAllByCityProvinceId(provinceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Citizen getCitizenById(Long id) {
      return  citizenRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Cidadao nao encontrado"));
    }

    @Override
    @Transactional
    public String verifyAccount(String otp) {
        Citizen citizen = citizenRepository.findByOtp(otp).orElseThrow(()->
                new EntityNotFoundException("Cidadao nao encontrado"));
        citizen.setVerified(true);
        citizen.setOtp(null);
        citizenRepository.save(citizen);
        return "A tua conta foi verificada com sucesso!";
    }

    @Override
    @Transactional
    public String generateOTPForCitizen(String phone) {
        Citizen citizen = citizenRepository.findByPhone(phone).orElseThrow(()->
                new EntityNotFoundException("Cidadão não encontrado, não foi possivel gerar o seu OTP!"));
        citizen.setOtp(null);
        String otp = generateOtp(6);
        citizen.setOtp(passwordEncoder.encode(otp));

        return "O seu código de acesso é:" + otp;
    }

    private static String generateOtp(int length){
        String otp="";
        int x;
        char[] chars = new char[length];

        for (int i=0; i < length; i++){
            Random random = new Random();
            x = random.nextInt( 9);
            chars[i] = Integer.toString(x).toCharArray()[0];

        }

        otp = new String(chars);
        return otp.trim();
    }
}
