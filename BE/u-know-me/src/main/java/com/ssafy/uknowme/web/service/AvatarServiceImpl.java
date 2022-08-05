package com.ssafy.uknowme.web.service;

import com.ssafy.uknowme.model.dto.AvatarRequestDto;
import com.ssafy.uknowme.web.domain.Avatar;
import com.ssafy.uknowme.web.domain.Member;
import com.ssafy.uknowme.web.repository.AvatarRepository;
import com.ssafy.uknowme.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService{

    private final AvatarRepository avatarRepository;

    private final MemberRepository memberRepository;

    @Value("${file.directory}")
    private String fileDirectory;

    @Override
    public void save(AvatarRequestDto avatarRequestDto, MultipartFile file) {
        // 서버에 저장될 파일 이름 <UUID.확장자>로 구성된다.
        String savedFileName = getSavedFileName(file);

        // 파일을 저장하는 메서드
        if (!saveFile(file, savedFileName)) {
            log.info("error");
            return;
        }

        // DB에 새로 만들어진 아바타의 정보를 저장한다.
        Avatar avatar = Avatar.builder()
                .name(avatarRequestDto.getName())
                .frequency(0)
                .file(savedFileName)
                .build();

        avatarRepository.save(avatar);
    }

    @Override
    @Transactional(readOnly = true)
    public Resource downloadAvatar() {
        Optional<Member> optionalMember = memberRepository.findById("mungmnb777");

        Member findMember = optionalMember.orElseThrow(NoSuchElementException::new);

        Avatar usingAvatar = findMember.getAvatar();

        String fileName = usingAvatar.getFile();

        return getUrlResource(fileName);
    }

    private UrlResource getUrlResource(String fileName) {
        try {
            return new UrlResource("file:" + getFullPath(fileName));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean saveFile(MultipartFile file, String savedFileName) {
        try {
            // 파일 객체를 생성한다.
            File folder = new File(getFullPath(savedFileName));

            // 해당 디렉토리에 폴더가 없다면 폴더를 생성한다.
            if (!folder.exists()) folder.mkdirs();

            // 파일을 저장한다.
            file.transferTo(folder);

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private String getFullPath(String fileName) {
        return fileDirectory + File.separator + fileName;
    }

    private String getSavedFileName(MultipartFile file) {
        // 클라이언트가 업로드하고자 하는 파일의 실제 이름이다.
        String originFilename = file.getOriginalFilename();

        // 확장자가 무엇인지 확인하기 위해 위치를 확인한다.
        int originExtensionIndex = originFilename.lastIndexOf(".");

        // 랜덤 UUID값을 서버에 저장한다.
        String uuid = String.valueOf(UUID.randomUUID());

        // 실제 확장자 부분을 붙여 저장한다.
        String extension = originFilename.substring(originExtensionIndex);

        return uuid + extension;
    }
}
