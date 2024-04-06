package com.shandilya.codes.controller;

import com.shandilya.codes.model.NobelPrizeWinner;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/ai/nobel")
public class NobelPrizeWinnerController {

    @Value("classpath:/prompts/usr-nobel-prize-winner.st")
    private Resource nobelPrizeWinnerPrompt;

    private final OpenAiChatClient chatClient;

    @Autowired
    public NobelPrizeWinnerController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/{category}/{year}")
    public NobelPrizeWinner getWinner(@PathVariable("year") String year,
                                      @PathVariable("category") String category) {
        BeanOutputParser<NobelPrizeWinner> parser = new BeanOutputParser<>(NobelPrizeWinner.class);

        PromptTemplate promptTemplate = new PromptTemplate(nobelPrizeWinnerPrompt);
        Message message = promptTemplate.createMessage(Map.of("category", category, "year", year, "format", parser.getFormat()));


        Prompt prompt = new Prompt(List.of(message));
        Generation result = chatClient.call(prompt).getResult();
        return parser.parse(result.getOutput().getContent());
    }
}