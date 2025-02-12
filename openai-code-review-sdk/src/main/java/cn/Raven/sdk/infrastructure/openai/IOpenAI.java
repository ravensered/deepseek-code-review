package cn.Raven.sdk.infrastructure.openai;

import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.Raven.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

/**
 * @Description
 * @Author susu
 * @Date 2025/2/12
 */
public interface IOpenAI {
    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;
}
