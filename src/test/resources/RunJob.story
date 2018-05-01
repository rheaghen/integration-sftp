Scenario: Run job SendFilesToSAP

Given the input data is loaded
When the batch job SendFilesToSAP is run
Then the job execution should be COMPLETED
And the output data should match the expected data
