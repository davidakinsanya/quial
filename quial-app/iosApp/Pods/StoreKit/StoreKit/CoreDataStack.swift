//
//  Created by Jesse Squires
//  http://www.jessesquires.com
//
//
//  Documentation
//  http://www.jessesquires.com/JSQCoreDataKit
//
//
//  GitHub
//  https://github.com/jessesquires/JSQCoreDataKit
//
//
//  License
//  Copyright (c) 2015 Jesse Squires
//  Released under an MIT license: http://opensource.org/licenses/MIT
//

import Foundation
import CoreData


/// Describes a child managed object context.
public typealias ChildManagedObjectContext = NSManagedObjectContext

/**
An instance of `CoreDataStack` encapsulates the entire Core Data stack for a SQLite store type.
It manages the managed object model, the persistent store coordinator, and the main managed object context.
It provides convenience methods for initializing a stack for common use-cases as well as creating child contexts.
*/
public final class CoreDataStack: CustomStringConvertible {

    // MARK: Properties

    /// The model for the stack.
    public let model: CoreDataModel

    /// The main managed object context for the stack.
    public let context: NSManagedObjectContext

    /// The persistent store coordinator for the stack.
    public let storeCoordinator: NSPersistentStoreCoordinator

    // MARK: Initialization

    /**
    Constructs a new `CoreDataStack` instance with the specified `model`, `storeType`, `options`, and `concurrencyType`.

    - parameter model:           The model describing the stack.
    - parameter options:         A dictionary containing key-value pairs that specify options for the store.
                                 The default contains `true` for the following keys:
                                 `NSMigratePersistentStoresAutomaticallyOption`, `NSInferMappingModelAutomaticallyOption`.
    - parameter concurrencyType: The concurrency pattern to use for the managed object context. 
                                 The default is `.MainQueueConcurrencyType`.

    - returns: A new `CoreDataStack` instance.
    */
    public init(model: CoreDataModel,
        options: [String : Bool]? = [NSMigratePersistentStoresAutomaticallyOption : true, NSInferMappingModelAutomaticallyOption : true],
                concurrencyType: NSManagedObjectContextConcurrencyType = .mainQueueConcurrencyType) {

            self.model = model
            storeCoordinator = NSPersistentStoreCoordinator(managedObjectModel: model.managedObjectModel)

            do {
                try storeCoordinator.addPersistentStore(ofType: model.storeType.description,
                                                        configurationName: nil,
                                                        at: model.storeURL as URL?,
                    options: options)
            }
            catch {
                fatalError("*** Error adding persistent store: \(error)")
            }

            context = NSManagedObjectContext(concurrencyType: concurrencyType)
            context.persistentStoreCoordinator = storeCoordinator
    }

    // MARK: Child contexts

    /**
    Creates a new child managed object context with the specified `concurrencyType` and `mergePolicyType`.

    - parameter concurrencyType: The concurrency pattern to use for the managed object context. The default is `.MainQueueConcurrencyType`.
    - parameter mergePolicyType: The merge policy to use for the manged object context. The default is `.MergeByPropertyObjectTrumpMergePolicyType`.

    - returns: A new child managed object context with the given concurrency and merge policy types.
    */
    public func childManagedObjectContext(concurrencyType: NSManagedObjectContextConcurrencyType = .mainQueueConcurrencyType,
                                          mergePolicyType: NSMergePolicyType = .mergeByPropertyObjectTrumpMergePolicyType) -> ChildManagedObjectContext {

            let childContext = NSManagedObjectContext(concurrencyType: concurrencyType)
        childContext.parent = context
        childContext.mergePolicy = NSMergePolicy(merge: mergePolicyType)
            return childContext
    }

    // MARK: CustomStringConvertible

    /// :nodoc:
    public var description: String {
        get {
            return "<\(CoreDataStack.self): model=\(model.name), context=\(context)>"
        }
    }
    
}
