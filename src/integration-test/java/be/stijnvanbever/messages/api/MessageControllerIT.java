package be.stijnvanbever.messages.api;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.test.MessageTestProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAllExistingMessages() throws Exception {
        mockMvc.perform(get("/api/v1/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(5)));
    }

    @ParameterizedTest
    @CsvSource({"Farnsworth,1", "Bender,1", "Hermes,1", "Fry,2"})
    public void shouldReturnMessages_When_ProvidingSender(String sender, Integer count) throws Exception {
        mockMvc.perform(get("/api/v1/messages?sender={sender}", sender))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(count)));
    }

    @ParameterizedTest
    @CsvSource({"Hermes,0","Farnsworth,1", "Bender,2", "Fry,2"})
    public void shouldReturnMessages_When_ProvidingReceiver(String receiver, Integer count) throws Exception {
        mockMvc.perform(get("/api/v1/messages?receiver={receiver}", receiver))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(count)));
    }

    @Test
    public void shouldAddMessage_When_Posting(@Autowired ObjectMapper objectMapper) throws Exception {
        Message message = MessageTestProvider.aMessage();
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        Message createdMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);
        assertThat(createdMessage.getId()).isNotNull();
        assertThat(createdMessage.getSender()).isEqualTo(message.getSender());
        assertThat(createdMessage.getReceiver()).isEqualTo(message.getReceiver());
        assertThat(createdMessage.getSubject()).isEqualTo(message.getSubject());
        assertThat(createdMessage.getContent()).isEqualTo(message.getContent());
        assertThat(createdMessage.getSentDate()).isEqualTo(message.getSentDate());
    }
}