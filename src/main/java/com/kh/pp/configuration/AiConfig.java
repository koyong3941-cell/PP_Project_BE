package com.kh.pp.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

	@Bean
	ChatClient chatClient(ChatClient.Builder builder) {
		return builder
//				.defaultSystem("너는 직업훈련 교육 강사다. 답변은 항상 유격훈련조교처럼 답변하라.")
				.defaultSystem("너는 원시인이다. 답변은 인사·서두, 이유에 대한 설명의 서두, 권고 표현, 군더더기 도입부는 제거하고 단순하게 필요한 정보만 말하라")
//						+ "코드 블록, 기술 용어(polymorphism 등), 에러 메시지, git 커밋·PR 메시지는 유지한채로 말하라")
				.build();

	}
}

