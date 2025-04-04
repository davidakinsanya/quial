/**
 Copyright 2018 Google Inc. All rights reserved.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at:

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

#import <FBLPromises/FBLPromise.h>

NS_ASSUME_NONNULL_BEGIN

@interface FBLPromise<Value>(AlwaysAdditions)

typedef void (^FBLPromiseAlwaysWorkBlock)(void) NS_SWIFT_UNAVAILABLE("");

/**
 @param work A block that always executes, no matter if the receiver is rejected or fulfilled.
 @return A new pending promise to be resolved with same resolution as the receiver.
 */
- (FBLPromise *)always:(FBLPromiseAlwaysWorkBlock)work NS_SWIFT_UNAVAILABLE("");

/**
 @param queue A queue to dispatch on.
 @param work A block that always executes, no matter if the receiver is rejected or fulfilled.
 @return A new pending promise to be resolved with same resolution as the receiver.
 */
- (FBLPromise *)onQueue:(dispatch_queue_t)queue
                 always:(FBLPromiseAlwaysWorkBlock)work NS_REFINED_FOR_SWIFT;

@end

/**
 Convenience dot-syntax wrappers for `FBLPromise` `always` operators.
 Usage: promise.always(^{...})
 */
@interface FBLPromise<Value>(DotSyntax_AlwaysAdditions)

- (FBLPromise* (^)(FBLPromiseAlwaysWorkBlock))always FBL_PROMISES_DOT_SYNTAX
    NS_SWIFT_UNAVAILABLE("");
- (FBLPromise* (^)(dispatch_queue_t, FBLPromiseAlwaysWorkBlock))alwaysOn FBL_PROMISES_DOT_SYNTAX
    NS_SWIFT_UNAVAILABLE("");

@end

NS_ASSUME_NONNULL_END
